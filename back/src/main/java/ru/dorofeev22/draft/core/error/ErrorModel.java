package ru.dorofeev22.draft.core.error;

public class ErrorModel {
    
    // for jackson
    public ErrorModel() {
    }
    
    private String errorCode;
    private String errorMessage;
    
    public ErrorModel(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}