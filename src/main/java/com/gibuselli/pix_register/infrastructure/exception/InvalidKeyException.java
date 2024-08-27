package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidKeyException extends RuntimeException {

    private static final String MESSAGE = "Tipo de chave inválido - %s";

    public InvalidKeyException(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
