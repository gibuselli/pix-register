package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.pixkey.KeyType;

import java.util.ArrayList;
import java.util.List;

public class PixKeyValidatorContext {

    private final List<PixKeyValidator> validators = new ArrayList<>();

    public void add(PixKeyValidator validator) {
        validators.add(validator);
    }

    public void validate(
            final Account account,
            final KeyType keyType,
            final String keyValue) {

        for (PixKeyValidator validator : validators) {
            validator.validate(account, keyType, keyValue);
        }
    }

}
