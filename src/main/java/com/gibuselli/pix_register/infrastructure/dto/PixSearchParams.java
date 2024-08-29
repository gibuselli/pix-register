package com.gibuselli.pix_register.infrastructure.dto;

import com.gibuselli.pix_register.domain.pixkey.KeyType;

import java.time.LocalDate;

public record PixSearchParams(
        KeyType keyType,
        String agency,
        String accountNumber,
        String customerName,
        LocalDate createdAt,
        LocalDate disabledAt
) {}
