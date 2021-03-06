package ru.dorofeev22.draft.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.dorofeev22.draft.core.error.service.ErrorModel;
import ru.dorofeev22.draft.core.utils.UrlParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static ru.dorofeev22.draft.core.utils.WebUtils.createPathParameters;

@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class BaseTestRestService {

    protected final static ResultMatcher OK_STATUS = MockMvcResultMatchers.status().isOk();
    private final static ResultMatcher ERROR_4XX_STATUS = MockMvcResultMatchers.status().is4xxClientError();
    private final static HttpHeaders mandatoryHeaders = new HttpHeaders() {{
        add(HttpHeaders.USER_AGENT, "Application test");
    }};

    private final static String SLASH = "/";

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    protected abstract String getPath();

    protected MvcResult getById(String id, ResultMatcher resultMatcher) throws Exception {
        return get(getUrl(id), resultMatcher);
    }
    
    protected MvcResult get(List<ImmutablePair<String, UrlParam>> parameters, ResultMatcher resultMatcher) throws Exception {
        return get(getPath().concat(createPathParameters(parameters)), resultMatcher);
    }
    
    protected ErrorModel getByIdWithClientError(String id) throws Exception {
        return toErrorResponse(getById(id, ERROR_4XX_STATUS));
    }
    
    protected ErrorModel getWithClientError(List<ImmutablePair<String, UrlParam>> parameters) throws Exception {
        return toErrorResponse(get(parameters, ERROR_4XX_STATUS));
    }

    protected MvcResult get(String url, ResultMatcher resultMatcher) throws Exception {
        return executeRequest(MockMvcRequestBuilders.get(url).headers(mandatoryHeaders), resultMatcher);
    }

    protected MvcResult post(String requestBody, ResultMatcher resultMatcher) throws Exception {
        return executeRequest(
                MockMvcRequestBuilders
                        .post(getPath())
                        .headers(mandatoryHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody),
                resultMatcher);
        
    }

    protected MvcResult post(String extraPath, MockMultipartFile file) throws Exception {
        return post(extraPath, file, OK_STATUS);
    }

    protected MvcResult post(String extraPath, MockMultipartFile file, ResultMatcher resultMatcher) throws Exception {
        return executeRequest(
                multipart(getPath().concat(extraPath)).file(file).headers(mandatoryHeaders),
                resultMatcher);
    }
    
    protected MvcResult put(String id, String requestBody, ResultMatcher resultMatcher) throws Exception {
        return executeRequest(
                MockMvcRequestBuilders.put(getUrl(id))
                        .headers(mandatoryHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody),
                resultMatcher);
    }
    
    private String getUrl(String id) {
        return getPath().concat(SLASH).concat(id);
    }
    
    private MvcResult executeRequest(MockHttpServletRequestBuilder requestBuilder, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(requestBuilder).andExpect(resultMatcher).andReturn();
    }
    
    private ErrorModel toErrorResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorModel.class);
    }
    
}