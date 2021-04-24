package ru.dorofeev22.draft.core.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dorofeev22.draft.core.error.ExecuteRequestError;
import ru.dorofeev22.draft.core.utils.UrlParam;

import java.util.List;
import java.util.function.Function;

import static ru.dorofeev22.draft.core.utils.WebUtils.createUrl;

@Service
public class RestTemplateService {

    private static class RequestExecutionModel {
        public final String url;
        public final String request;

        public RequestExecutionModel(String url, String request) {
            this.url = url;
            this.request = request;
        }

        public RequestExecutionModel(String url) {
            this(url, null);
        }
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RestTemplateService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T executeGetQuery(@NotNull final String path,
                                 final List<ImmutablePair<String, UrlParam>> parameters,
                                 @NotNull Class<T> responseClass) {
        final String url = createUrl(path, parameters);
        return readResponse(url, execute(new RequestExecutionModel(url), this::executeGetQuery), responseClass);
    }

    public <T> T executePostQuery(@NotNull final String url, @NotNull final IRequestModel request, final Class<T> responseClass) {
        final String response = execute(new RequestExecutionModel(url, writeRequest(url, request)), this::executePostQuery);
        return StringUtils.isBlank(response) ? null : readResponse(url, response, responseClass);
    }

    private String executeGetQuery(@NotNull final RequestExecutionModel requestExecutionModel) {
        return restTemplate.getForObject(requestExecutionModel.url, String.class);
    }

    private String executePostQuery(@NotNull final RequestExecutionModel requestExecutionModel) {
        return restTemplate.postForObject(requestExecutionModel.url, requestExecutionModel.request, String.class);
    }

    private String writeRequest(@NotNull final String url, @NotNull final IRequestModel request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("Couldn't create request body {} for {}, error: {}", request.toLogString(), url, e);
            throw new ExecuteRequestError(url, e.getLocalizedMessage());
        }
    }

    private String execute(@NotNull final RequestExecutionModel requestExecutionModel,
                           @NotNull final Function<RequestExecutionModel, String> requestFunction) {
        try {
            return requestFunction.apply(requestExecutionModel);
        } catch (Throwable throwable) {
            log.error("Couldn't execute request to {}, error: {}", requestExecutionModel, throwable);
            throw new ExecuteRequestError(requestExecutionModel.url, throwable.getLocalizedMessage());
        }
    }

    private <T> T readResponse(@NotNull final String url, @NotNull final String response, @NotNull Class<T> responseClass) {
        try {
            return objectMapper.readValue(response, responseClass);
        } catch (Exception e) {
            log.error("Couldn't read response {} from {}, error: {}", response, url, e);
            throw new ExecuteRequestError(url, e.getLocalizedMessage());
        }

    }

}