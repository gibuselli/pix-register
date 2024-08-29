package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidPersonTypeException extends RuntimeException {

    private static final String MESSAGE = "Tipo de cliente inválido - %s";

    public InvalidPersonTypeException(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
