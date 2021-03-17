package ru.dorofeev22.draft.core.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.dorofeev22.draft.core.utils.WebUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ApiCallLoggingHelper {
    
    private static final String REQUEST_INFO_DELIMITER = ", ";
    private static final String REQUEST_PARAM_DELIMITER = "&";
    private static final Set<String> needToBeHidden = new HashSet<String>() {{
        add("password");
        add("psw");
    }};
    private static final Set<String> bodyMediaTypes = new HashSet<String>() {{
        add(MediaType.APPLICATION_JSON_VALUE);
        add(MediaType.APPLICATION_XML_VALUE);
        add(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }};
    
    private final ObjectMapper objectMapper;
    
    public ApiCallLoggingHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public String createRequestInfo(@NotNull final MultiReadHttpServletRequest httpServletRequest) {
        StringBuilder logInfo = new StringBuilder("API call: ")
                .append(httpServletRequest.getMethod()).append(" ")
                .append(httpServletRequest.getRequestURI());
        addParameters(logInfo, httpServletRequest);
        addRequestBody(logInfo, httpServletRequest);
        logInfo.append(REQUEST_INFO_DELIMITER).append(httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        logInfo.append(REQUEST_INFO_DELIMITER).append("Ip: ").append(WebUtils.extractIp(httpServletRequest));
        // todo: add ip address;
        return logInfo.toString();
    }
    
    private void addParameters(@NotNull final StringBuilder logInfo, @NotNull final MultiReadHttpServletRequest r) {
        if (r.getParameterMap().size() > 0)
            logInfo.append("?").append(
                    r.getParameterMap().entrySet().stream()
                            .map(this::creteUrlParameter)
                            .collect(Collectors.joining(REQUEST_PARAM_DELIMITER)));
    }
    
    private String creteUrlParameter(Map.Entry<String, String[]> e) {
        return Arrays.stream(e.getValue())
                .map(v -> e.getKey() + "=" + (needToBeHidden.contains(e.getKey()) ? "***" : v))
                .collect(Collectors.joining(REQUEST_PARAM_DELIMITER));
    }
    
    private void addRequestBody(@NotNull final StringBuilder logInfo, @NotNull final MultiReadHttpServletRequest r) {
        if (bodyMediaTypes.contains(r.getContentType())) {
            try {
                final String body = r.getReader().lines().collect(Collectors.joining());
                if (StringUtils.isNotBlank(body)) {
                    JsonNode jsonNode = objectMapper.readTree(body);
                    for (String k : needToBeHidden) {
                        if (jsonNode.findValue(k) != null)
                            ((ObjectNode) jsonNode).put(k, "***");
                    }
                    logInfo.append(" ").append(jsonNode.toString());
                }
            } catch (Exception e) {
                logInfo.append(REQUEST_INFO_DELIMITER).append("Couldn't extract request body");
            }
        }
    }
}