package com.gibuselli.pix_register.interfaces;

import com.gibuselli.pix_register.domain.pixkey.PixKeyService;
import com.gibuselli.pix_register.infrastructure.rest.PixRegisterRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix-key")
public class PixRegisterController {

    public final PixKeyService pixKeyService;

    public PixRegisterController(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    @PostMapping
    public ResponseEntity<String> registerPixKey(@Validated @RequestBody final @NotNull PixRegisterRequest request) {
        return null;
    }

    @PatchMapping
    public ResponseEntity<String> updatePixKey() {
        return null;
    }

    @PatchMapping
    public ResponseEntity<String> getPixKey() {
        return null;
    }

    @PatchMapping
    public ResponseEntity<String> disablePixKey() {
        return null;
    }
}
