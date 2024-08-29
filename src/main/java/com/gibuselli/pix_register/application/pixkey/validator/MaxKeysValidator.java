package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.MaxKeysException;

public class MaxKeysValidator implements PixKeyValidator {
    @Override
    public void validate(Account account, KeyType keyType, String keyValue) {
        final var maxKeys = account.isLegalPerson() ? 20 : 5;
        final var keyCount = account.getPixKeys().size();

        if (keyCount >= maxKeys) {
            throw new MaxKeysException();
        }
    }
}
