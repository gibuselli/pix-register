package com.gibuselli.pix_register.domain.customer;

import com.gibuselli.pix_register.infrastructure.exception.InvalidPersonType;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public enum PersonType {

    FISICA,

    JURIDICA;

    public static PersonType fromValue(final @NotNull String value) {
        return Arrays.stream(PersonType.class.getEnumConstants())
                .filter(e -> value.equalsIgnoreCase(e.name()))
                .findFirst()
                .orElseThrow(() -> new InvalidPersonType(value));
    }
}
