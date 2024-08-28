package com.gibuselli.pix_register.infrastructure.rest.update;

import java.util.UUID;

public record PixUpdateResponse(
        UUID id,
        String keyType,
        String keyValue,
        String accountType,
        String agency,
        String account,
        String customerName,
        String customerLastName,
        String keyRegisterDate
) {}
