package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.MaxKeysException;

public class MaxKeysValidator implements PixKeyValidator {
    @Override
    public void validate(Customer customer, KeyType keyType, String keyValue) {
        final var maxKeys = customer.isLegalPerson() ? 20 : 5;
        final var keyCount = customer.getPixKeys().size();

        if (keyCount >= maxKeys) {
            throw new MaxKeysException();
        }
    }
}
