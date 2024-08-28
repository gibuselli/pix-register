package com.gibuselli.pix_register.infrastructure.exception;

import java.util.UUID;

public class PixKeyNotFoundException extends RuntimeException {

    private static final String MESSAGE = "UUID não encontrado - %s";

    public PixKeyNotFoundException(UUID keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
