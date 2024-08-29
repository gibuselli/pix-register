package com.gibuselli.pix_register.domain.account;

import com.gibuselli.pix_register.infrastructure.exception.InvalidPersonTypeException;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public enum PersonType {

    FISICA,

    JURIDICA;

    public static PersonType fromValue(final @NotNull String value) {
        return Arrays.stream(PersonType.class.getEnumConstants())
                .filter(e -> value.equalsIgnoreCase(e.name()))
                .findFirst()
                .orElseThrow(() -> new InvalidPersonTypeException(value));
    }
}
