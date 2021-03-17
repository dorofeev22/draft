package ru.dorofeev22.draft.core.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ApiCallLoggingHelper {
    
    private static final String DELIMITER = ", ";
    private static final String REQUEST_PARAM_DELIMITER = "&";
    
    public static String createRequestInfo(@NotNull final MultiReadHttpServletRequest httpServletRequest) {
        StringBuilder logInfo = new StringBuilder("API call: ")
                .append(httpServletRequest.getMethod()).append(" ")
                .append(httpServletRequest.getRequestURI());
        addParameters(logInfo, httpServletRequest);
        addRequestBody(logInfo, httpServletRequest);
        logInfo.append(DELIMITER).append(httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        // todo: add ip address;
        return logInfo.toString();
    }
    
    private static void addParameters(@NotNull final StringBuilder logInfo, @NotNull final MultiReadHttpServletRequest r) {
        if (r.getParameterMap().size() > 0)
            logInfo.append("?").append(
                    r.getParameterMap().entrySet().stream()
                            .map(e -> Arrays.stream(e.getValue())
                                    .map(v -> e.getKey()+"="+v).collect(Collectors.joining(REQUEST_PARAM_DELIMITER)))
                            .collect(Collectors.joining(REQUEST_PARAM_DELIMITER)));
    }
    
    private static void addRequestBody(@NotNull final StringBuilder logInfo, @NotNull final MultiReadHttpServletRequest r) {
        try {
            final String body = r.getReader().lines().collect(Collectors.joining(DELIMITER));
            if (StringUtils.isNotBlank(body))
                logInfo.append(" ").append(body);
        } catch (Exception e) {
            logInfo.append(DELIMITER).append("Couldn't extract request body");
        }
    }
}