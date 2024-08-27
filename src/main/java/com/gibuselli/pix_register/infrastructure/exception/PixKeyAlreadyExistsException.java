package com.gibuselli.pix_register.infrastructure.exception;

public class PixKeyAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Já existe uma chave PIX cadastrada com esses dados: %s - %s ";

    public PixKeyAlreadyExistsException(String keyType, String keyValue) {
        super(String.format(MESSAGE, keyType, keyValue));
    }
}
