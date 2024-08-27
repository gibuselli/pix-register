package com.gibuselli.pix_register.infrastructure.http;

import java.util.HashMap;
import java.util.Map;

public class ErrorsResponse {
    private String message;

    private Map<String, Object> errors = new HashMap<>();

    public ErrorsResponse(String message, Map<String, Object> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorsResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addErrors(String errorKey, Object errorValue) {
        errors.put(errorKey, errorValue);
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}