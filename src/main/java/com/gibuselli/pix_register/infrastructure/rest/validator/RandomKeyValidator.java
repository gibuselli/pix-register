package com.gibuselli.pix_register.infrastructure.rest.validator;

import jakarta.validation.ConstraintValidatorContext;

public class RandomKeyValidator implements PixKeyRequestValidator {
    @Override
    public boolean isValid(String randomKey, ConstraintValidatorContext context) {
        if (randomKey == null || !randomKey.matches("[a-zA-Z0-9]{1,36}")) {
            context
                    .buildConstraintViolationWithTemplate("Chave aleatória inválida. Deve conter no máximo 36 caracteres alfanuméricos.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
