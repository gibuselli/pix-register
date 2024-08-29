package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.CnpjOrCpfAlreadyRegistered;
import com.gibuselli.pix_register.infrastructure.exception.InvalidKeyForNaturalPersonException;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;
import static com.gibuselli.pix_register.domain.pixkey.KeyType.CPF;

public class NaturalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Account account, KeyType keyType, String keyValue) {
        if (account.isLegalPerson()) {
            return;
        }

        if (CNPJ.equals(keyType)) {
            throw new InvalidKeyForNaturalPersonException();
        }

        final var hasCpfKey = account.getPixKeys().stream().anyMatch(key -> CPF.equals(keyType));

        if (hasCpfKey) {
            throw new CnpjOrCpfAlreadyRegistered(keyType.name());
        }
    }
}
