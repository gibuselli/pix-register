package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.pixkey.KeyType;

public interface PixKeyValidator {
    void validate(Account account, KeyType keyType, String keyValue);
}
