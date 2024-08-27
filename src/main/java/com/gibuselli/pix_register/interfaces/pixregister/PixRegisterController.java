package com.gibuselli.pix_register.interfaces.pixregister;

import com.gibuselli.pix_register.infrastructure.rest.PixRegisterRequest;
import com.gibuselli.pix_register.infrastructure.rest.PixRegisterResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> getPixKey() {
        return null;
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updatePixKey() {
        return null;
    }

    @PatchMapping("/disable")
    public ResponseEntity<String> disablePixKey() {
        return null;
    }
}
