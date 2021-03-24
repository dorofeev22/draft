package ru.dorofeev22.draft.core.error.service;

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
    
    @Override
    public String toString() {
        return "{ errorCode: " + errorCode + ", errorMessage: " + errorMessage + " }";
    }
}