package com.gibuselli.pix_register.infrastructure.dto;

import com.gibuselli.pix_register.domain.account.AccountType;
import com.gibuselli.pix_register.domain.account.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;

public record PixRegisterData(
        KeyType keyType,
        String keyValue,
        AccountType accountType,
        PersonType personType,
        String agency,
        String accountNumber,
        String customerName,
        String customerLastName
) {}
