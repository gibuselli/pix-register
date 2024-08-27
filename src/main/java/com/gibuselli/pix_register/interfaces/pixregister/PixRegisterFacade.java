package com.gibuselli.pix_register.interfaces.pixregister;

import com.gibuselli.pix_register.domain.pixkey.PixKeyService;
import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import com.gibuselli.pix_register.infrastructure.rest.PixRegisterRequest;
import com.gibuselli.pix_register.infrastructure.rest.PixRegisterResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PixRegisterFacade {

    private final PixKeyService pixKeyService;

    public PixRegisterFacade(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    public PixRegisterResponse registerPixKey(final @NotNull PixRegisterRequest request) {
        final var pixRegisterData = new PixRegisterData(
                request.getKeyType(),
                request.getKeyValue(),
                request.getAccountType(),
                request.getPersonType(),
                request.getAgency(),
                request.getAccount(),
                request.getCustomerName(),
                request.getCustomerLastName()
        );

        return new PixRegisterResponse(pixKeyService.registerPixKey(pixRegisterData));
    }
}
