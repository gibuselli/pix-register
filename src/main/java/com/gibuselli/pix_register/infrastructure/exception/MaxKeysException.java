package com.gibuselli.pix_register.infrastructure.exception;

public class MaxKeysException extends RuntimeException {

    private static final String MESSAGE = "Limite de chaves atingido.";

    public MaxKeysException() {
        super(MESSAGE);
    }
}
