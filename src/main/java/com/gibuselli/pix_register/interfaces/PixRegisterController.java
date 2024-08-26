package com.gibuselli.pix_register.interfaces;

import com.gibuselli.pix_register.domain.pixkey.PixKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pix-key")
public class PixRegisterController {

    public final PixKeyService pixKeyService;

    public PixRegisterController(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    @PostMapping
    public ResponseEntity<String> registerPixKey() {
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
