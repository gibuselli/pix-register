package com.gibuselli.pix_register.infrastructure.dto;

import com.gibuselli.pix_register.domain.customer.AccountType;
import com.gibuselli.pix_register.domain.customer.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;

public record PixRegisterData(
        KeyType keyType,
        String keyValue,
        AccountType accountType,
        PersonType personType,
        String agency,
        String account,
        String customerName,
        String customerLastName
) {}
