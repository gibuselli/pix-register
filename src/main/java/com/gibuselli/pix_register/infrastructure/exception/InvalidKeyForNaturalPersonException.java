package com.gibuselli.pix_register.infrastructure.exception;

public class InvalidKeyForNaturalPersonException extends RuntimeException {

    private static final String MESSAGE = "Pessoa física não pode ter chave CNPJ";

    public InvalidKeyForNaturalPersonException() {
        super(MESSAGE);
    }
}
