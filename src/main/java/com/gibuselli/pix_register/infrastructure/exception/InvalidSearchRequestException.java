package com.gibuselli.pix_register.infrastructure.exception;


public class InvalidSearchRequestException extends RuntimeException {

    public InvalidSearchRequestException(String message) {
        super(message);
    }
}
