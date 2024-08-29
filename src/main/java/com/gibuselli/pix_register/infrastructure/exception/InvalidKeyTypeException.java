package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidKeyTypeException extends RuntimeException {

    private static final String MESSAGE = "Tipo de chave inválida - %s";

    public InvalidKeyTypeException(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
