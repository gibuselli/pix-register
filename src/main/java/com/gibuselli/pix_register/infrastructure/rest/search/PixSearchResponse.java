package com.gibuselli.pix_register.infrastructure.rest.search;

import java.util.UUID;

public record PixSearchResponse(
        UUID id,
        String keyType,
        String keyValue,
        String accountType,
        String agency,
        String account,
        String customerName,
        String customerLastName,
        String createdAt,
        String disabledAt
) {

}
