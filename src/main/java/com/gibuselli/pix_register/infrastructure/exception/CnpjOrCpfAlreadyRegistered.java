package com.gibuselli.pix_register.infrastructure.exception;

public class CnpjOrCpfAlreadyRegistered extends RuntimeException {

    private static final String MESSAGE = "Usuário já cadastrou uma chave do tipo %s";

    public CnpjOrCpfAlreadyRegistered(String keyType) {
        super(String.format(MESSAGE, keyType));
    }
}
