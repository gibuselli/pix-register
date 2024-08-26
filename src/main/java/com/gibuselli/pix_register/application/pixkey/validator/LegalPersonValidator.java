package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import jakarta.validation.ValidationException;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;
import static com.gibuselli.pix_register.domain.pixkey.KeyType.CPF;

public class LegalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Customer customer, KeyType keyType, String keyValue) {
        if (customer.isNaturalPerson()) {
            return;
        }

        if (CPF.equals(keyType)) {
            throw new ValidationException("Tipo de chave inválida para pessoa física.");
        }

        final var hasCnpjKey = customer.getPixKeys().stream().anyMatch(key -> key.getType().equals(CNPJ));

        if (hasCnpjKey) {
            throw new ValidationException("Já existe uma chave CNPJ");
        }
    }
}
