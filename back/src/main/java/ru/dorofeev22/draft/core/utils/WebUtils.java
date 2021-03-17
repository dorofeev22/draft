package ru.dorofeev22.draft.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebUtils {
    
    private final static String QUESTION = "?";
    private final static String EQUAL = "=";
    private final static String AMPERSAND = "&";
    
    private static final String X_FORWARDED_FOR_HEADER_NAME = "X-Forwarded-For";
    private static final String X_REAL_IP_HEADER_NAME = "X-Real-IP";
    
    public static String createPathParameters(List<ImmutablePair<String, String>> parameters) {
        return QUESTION.concat(
                parameters.stream()
                        .map(p -> p.left.concat(EQUAL).concat(p.right))
                        .collect(Collectors.joining(AMPERSAND)));
    }
    
    
    public static Integer getIntParameterValue(@NotNull final Map<String, String[]> parameters, @NotNull final String parameterName) {
        return parameters.get(parameterName) != null ? Integer.valueOf(parameters.get(parameterName)[0]) : null;
    }
    
    public static String extractIp(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            String xForwardedForHeader = httpServletRequest.getHeader(X_FORWARDED_FOR_HEADER_NAME);
            String xRealIpHeader = httpServletRequest.getHeader(X_REAL_IP_HEADER_NAME);
            if (StringUtils.isNotBlank(xForwardedForHeader)) {
                return xForwardedForHeader;
            }
            if (StringUtils.isNotBlank(xRealIpHeader)) {
                return xRealIpHeader;
            }
            return httpServletRequest.getRemoteAddr();
        }
        return null;
    }
}