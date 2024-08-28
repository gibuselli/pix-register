package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.CnpjOrCpfAlreadyRegistered;
import com.gibuselli.pix_register.infrastructure.exception.InvalidKeyForNaturalPersonException;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;
import static com.gibuselli.pix_register.domain.pixkey.KeyType.CPF;

public class NaturalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Customer customer, KeyType keyType, String keyValue) {
        if (customer.isLegalPerson()) {
            return;
        }

        if (CNPJ.equals(keyType)) {
            throw new InvalidKeyForNaturalPersonException();
        }

        final var hasCpfKey = customer.getPixKeys().stream().anyMatch(key -> CPF.equals(keyType));

        if (hasCpfKey) {
            throw new CnpjOrCpfAlreadyRegistered(keyType.name());
        }
    }
}
