package com.gibuselli.pix_register.domain.customer;

import com.gibuselli.pix_register.infrastructure.exception.InvalidAccountType;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public enum AccountType {
    /** Conta corrente */
    CHECKING_ACCOUNT,

    /** Conta poupança */
    SAVINGS_ACCOUNT;

    public static AccountType fromValue(final @NotNull String value) {
        return Arrays.stream(AccountType.class.getEnumConstants())
                .filter(e -> value.equalsIgnoreCase(e.name()))
                .findFirst()
                .orElseThrow(() -> new InvalidAccountType(value));
    }
}
