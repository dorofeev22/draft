package ru.dorofeev22.draft.core.error;

public class BadParameterError extends BaseError {
    
    private final String parameterName;
    private final String parameterValue;
    
    public BadParameterError(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }
    
    public String getParameterName() {
        return parameterName;
    }
    
    public String getParameterValue() {
        return parameterValue;
    }
}