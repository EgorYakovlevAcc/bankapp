package com.presentation.demo.errors;

public abstract class MessageException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    protected void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return "[" + getErrorCode() + ":" + getErrorMessage() + "]";
    }
}
