package ru.dorofeev22.draft.core.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebUtils {
    
    private final static String QUESTION = "?";
    private final static String EQUAL = "=";
    private final static String AMPERSAND = "&";
    
    public static String createPath(List<ImmutablePair<String, String>> parameters) {
        return QUESTION.concat(
                parameters.stream()
                        .map(p -> p.left.concat(EQUAL).concat(p.right))
                        .collect(Collectors.joining(AMPERSAND)));
    }
    
    
    public static Integer getIntParameterValue(@NotNull final Map<String, String[]> parameters, @NotNull final String parameterName) {
        return parameters.get(parameterName) != null ? Integer.valueOf(parameters.get(parameterName)[0]) : null;
    }
}