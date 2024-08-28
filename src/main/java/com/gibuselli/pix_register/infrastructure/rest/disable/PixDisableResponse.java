package com.gibuselli.pix_register.infrastructure.rest.disable;

import java.util.UUID;

public record PixDisableResponse(
        UUID id,
        String keyType,
        String keyValue,
        String accountType,
        String disableTime
) {
}
