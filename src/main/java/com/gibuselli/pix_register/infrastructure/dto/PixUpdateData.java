package com.gibuselli.pix_register.infrastructure.dto;

import com.gibuselli.pix_register.domain.account.AccountType;

import java.util.UUID;

public record PixUpdateData(
        UUID id,
        AccountType accountType,
        String agency,
        String accountNumber,
        String customerName,
        String customerLastName
) {}
