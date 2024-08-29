package com.gibuselli.pix_register.infrastructure.rest.validator;

import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements PixKeyRequestValidator {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            context
                    .buildConstraintViolationWithTemplate("Email inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
