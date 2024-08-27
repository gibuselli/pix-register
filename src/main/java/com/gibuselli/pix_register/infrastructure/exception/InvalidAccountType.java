package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidAccountType extends RuntimeException {

    private static final String MESSAGE = "Tipo de conta inválida - %s";

    public InvalidAccountType(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
