package com.gibuselli.pix_register.interfaces.pixregister;

import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.exception.InvalidSearchRequestException;
import com.gibuselli.pix_register.infrastructure.rest.disable.PixDisableResponse;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterRequest;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterResponse;
import com.gibuselli.pix_register.infrastructure.dto.PixSearchParams;
import com.gibuselli.pix_register.infrastructure.rest.search.PixSearchResponse;
import com.gibuselli.pix_register.infrastructure.rest.update.PixUpdateRequest;
import com.gibuselli.pix_register.infrastructure.rest.update.PixUpdateResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/pix-key", produces = MediaType.APPLICATION_JSON_VALUE)
public class PixRegisterController {

    public final PixRegisterFacade pixRegisterFacade;

    public PixRegisterController(PixRegisterFacade pixRegisterFacade) {
        this.pixRegisterFacade = pixRegisterFacade;
    }

    @PostMapping
    public ResponseEntity<PixRegisterResponse> registerPixKey(
            @Validated @RequestBody final @NotNull PixRegisterRequest request) {
        return ResponseEntity.ok(pixRegisterFacade.registerPixKey(request));
    }

    @GetMapping
    public ResponseEntity<List<PixSearchResponse>> getPixKey(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String keyType,
            @RequestParam(required = false) String agency,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) LocalDate createdAt,
            @RequestParam(required = false) LocalDate disabledAt
    ) {
        if (id != null) {
            if (keyType != null
                    || agency != null
                    || accountNumber != null
                    || customerName != null
                    || createdAt != null
                    || disabledAt != null) {
                throw new InvalidSearchRequestException("ID não pode ser combinado com outras consultas.");
            }
            return ResponseEntity.ok(List.of(pixRegisterFacade.searchById(id)));
        }

        if (createdAt != null && disabledAt != null) {
            throw new InvalidSearchRequestException("Não é possível combinar consultas com datas de criação e desativação.");
        }

        final var searchParams =
                new PixSearchParams(
                        Optional.ofNullable(keyType)
                                .map(KeyType::fromValue)
                                .orElse(null),
                        agency,
                        accountNumber,
                        customerName,
                        createdAt,
                        disabledAt
                );

        final var results = pixRegisterFacade.searchByParams(searchParams);

        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(results);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PixUpdateResponse> updatePixKey(
            @PathVariable UUID id,
            @Validated@RequestBody final @NotNull PixUpdateRequest request) {
        return ResponseEntity.ok(pixRegisterFacade.updatePixKey(id, request));
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<PixDisableResponse> disablePixKey(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(pixRegisterFacade.disablePixKey(id));
    }
}
