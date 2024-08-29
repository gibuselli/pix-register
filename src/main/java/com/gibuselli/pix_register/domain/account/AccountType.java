package com.gibuselli.pix_register.domain.account;

import com.gibuselli.pix_register.infrastructure.exception.InvalidAccountTypeException;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public enum AccountType {
    CORRENTE,
    POUPANCA;

    public static AccountType fromValue(final @NotNull String value) {
        return Arrays.stream(AccountType.class.getEnumConstants())
                .filter(e -> value.equalsIgnoreCase(e.name()))
                .findFirst()
                .orElseThrow(() -> new InvalidAccountTypeException(value));
    }
}
