package com.gibuselli.pix_register.interfaces.pixregister;

import com.gibuselli.pix_register.domain.customer.AccountType;
import com.gibuselli.pix_register.domain.customer.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.domain.pixkey.PixKeyService;
import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import com.gibuselli.pix_register.infrastructure.dto.PixUpdateData;
import com.gibuselli.pix_register.infrastructure.rest.disable.PixDisableResponse;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterRequest;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterResponse;
import com.gibuselli.pix_register.infrastructure.dto.PixSearchParams;
import com.gibuselli.pix_register.infrastructure.rest.search.PixSearchResponse;
import com.gibuselli.pix_register.infrastructure.rest.update.PixUpdateRequest;
import com.gibuselli.pix_register.infrastructure.rest.update.PixUpdateResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PixRegisterFacade {

    private final PixKeyService pixKeyService;

    public PixRegisterFacade(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    public PixRegisterResponse registerPixKey(final @NotNull PixRegisterRequest request) {
        final var pixRegisterData = new PixRegisterData(
                KeyType.fromValue(request.getKeyType()),
                request.getKeyValue(),
                AccountType.fromValue(request.getAccountType()),
                PersonType.fromValue(request.getPersonType()),
                request.getAgency(),
                request.getAccount(),
                request.getCustomerName(),
                request.getCustomerLastName()
        );

        return new PixRegisterResponse(pixKeyService.registerPixKey(pixRegisterData));
    }

    public PixUpdateResponse updatePixKey(UUID id, @NotNull PixUpdateRequest request) {
        final var pixUpdateData = new PixUpdateData(
                id,
                AccountType.fromValue(request.getAccountType()),
                request.getAgency(),
                request.getAccount(),
                request.getCustomerName(),
                request.getCustomerLastName()
        );

        final var updatedPixKey = pixKeyService.updatePixKey(pixUpdateData);

        return new PixUpdateResponse(
                updatedPixKey.getId(),
                updatedPixKey.getType().name(),
                updatedPixKey.getValue(),
                updatedPixKey.getCustomer().getAccountType().name(),
                updatedPixKey.getCustomer().getAgency(),
                updatedPixKey.getCustomer().getAccount(),
                updatedPixKey.getCustomer().getName(),
                Optional.ofNullable(updatedPixKey.getCustomer().getLastName()).orElse(""),
                updatedPixKey.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

    }

    public PixDisableResponse disablePixKey(UUID id) {
        final var disabledPixKey = pixKeyService.disablePixKey(id);

        return new PixDisableResponse(
                disabledPixKey.getId(),
                disabledPixKey.getType().name(),
                disabledPixKey.getValue(),
                disabledPixKey.getCustomer().getAccountType().name(),
                Optional.ofNullable(disabledPixKey.getDisabledAt())
                        .map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .orElse(""));
    }

    public PixSearchResponse searchById(UUID id) {
        final var pixKey = pixKeyService.searchById(id);

        return new PixSearchResponse(
                pixKey.getId(),
                pixKey.getType().name(),
                pixKey.getValue(),
                pixKey.getCustomer().getAccountType().name(),
                pixKey.getCustomer().getAgency(),
                pixKey.getCustomer().getAccount(),
                pixKey.getCustomer().getName(),
                Optional.ofNullable(pixKey.getCustomer().getLastName()).orElse(""),
                pixKey.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                Optional.ofNullable(pixKey.getDisabledAt())
                        .map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .orElse("")
        );
    }

    public List<PixSearchResponse> searchByParams(PixSearchParams params) {
        List<PixSearchResponse> results = new ArrayList<>();

        final var pixKeys = pixKeyService.searchByParams(params);

        pixKeys.forEach((pixKey) -> {
            final var result =
                    new PixSearchResponse(
                            pixKey.getId(),
                            pixKey.getType().name(),
                            pixKey.getValue(),
                            pixKey.getCustomer().getAccountType().name(),
                            pixKey.getCustomer().getAgency(),
                            pixKey.getCustomer().getAccount(),
                            pixKey.getCustomer().getName(),
                            Optional.ofNullable(pixKey.getCustomer().getLastName()).orElse(""),
                            pixKey.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            Optional.ofNullable(pixKey.getDisabledAt())
                                    .map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                    .orElse("")
                    );

            results.add(result);
        });

        return results;
    }
}
