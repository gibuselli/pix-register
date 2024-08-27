package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.infrastructure.exception.InvalidKeyType;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum KeyType  {
    EMAIL,
    PHONE,
    CPF,
    CNPJ,
    RANDOM;

    public static KeyType fromValue(final @NotNull String value) {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidKeyType(value);
        }

        return Arrays.stream(KeyType.class.getEnumConstants())
                .filter(e -> value.equalsIgnoreCase(e.name()))
                .findFirst()
                .orElseThrow(() -> new InvalidKeyType(value));
    }
}
