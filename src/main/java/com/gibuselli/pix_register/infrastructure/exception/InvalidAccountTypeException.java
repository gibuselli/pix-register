package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidAccountTypeException extends RuntimeException {

    private static final String MESSAGE = "Tipo de conta inválida - %s";

    public InvalidAccountTypeException(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
