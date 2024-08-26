package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import jakarta.validation.ValidationException;

import static com.gibuselli.pix_register.domain.pixkey.KeyType.CNPJ;
import static com.gibuselli.pix_register.domain.pixkey.KeyType.CPF;

public class NaturalPersonValidator implements PixKeyValidator {
    @Override
    public void validate(Customer customer, KeyType keyType, String keyValue) {
        if (customer.isLegalPerson()) {
            return;
        }

        if (CNPJ.equals(keyType)) {
            throw new ValidationException("Tipo de chave inválida para pessoa física.");
        }

        final var hasCpfKey = customer.getPixKeys().stream().anyMatch(key -> key.getType().equals(CPF));

        if (hasCpfKey) {
            throw new ValidationException("Já existe uma chave CPF");
        }
    }
}
