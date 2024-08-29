package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.CnpjOrCpfAlreadyRegistered;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;

public class LegalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Account account, KeyType keyType, String keyValue) {
        if (account.isNaturalPerson()) {
            return;
        }

        final var hasCnpjKey = account.getPixKeys().stream().anyMatch(key -> CNPJ.equals(key.getType()));

        if (hasCnpjKey) {
            throw new CnpjOrCpfAlreadyRegistered(keyType.name());
        }
    }
}
