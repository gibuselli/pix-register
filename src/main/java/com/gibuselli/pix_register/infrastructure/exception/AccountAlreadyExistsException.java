package com.gibuselli.pix_register.infrastructure.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "Já existe uma conta com agência: %s e número: %s";

    public AccountAlreadyExistsException(String agency, String accountNumber) {
        super(String.format(MESSAGE, agency, accountNumber));
    }
}
