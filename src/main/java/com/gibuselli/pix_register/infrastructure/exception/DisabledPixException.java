package com.gibuselli.pix_register.infrastructure.exception;

import java.util.UUID;

public class DisabledPixException extends RuntimeException {

    private static final String MESSAGE = "Chave PIX %s desativada, não é possível alterá-la ou desativá-la novamente.";

    public DisabledPixException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
