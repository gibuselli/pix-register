package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidPersonType extends RuntimeException {

    private static final String MESSAGE = "Tipo de cliente inválido - %s";

    public InvalidPersonType(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
