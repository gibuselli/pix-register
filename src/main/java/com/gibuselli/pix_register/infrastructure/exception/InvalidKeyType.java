package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidKeyType extends RuntimeException {

    private static final String MESSAGE = "Tipo de chave inválida - %s";

    public InvalidKeyType(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
