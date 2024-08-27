package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.CnpjOrCpfAlreadyRegistered;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;

public class LegalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Customer customer, KeyType keyType, String keyValue) {
        if (customer.isNaturalPerson()) {
            return;
        }

        final var hasCnpjKey = customer.getPixKeys().stream().anyMatch(key -> CNPJ.equals(key.getType()));

        if (hasCnpjKey) {
            throw new CnpjOrCpfAlreadyRegistered(keyType.name());
        }
    }
}
