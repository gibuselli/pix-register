package com.gibuselli.pix_register.infrastructure.rest.validator;

import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements PixKeyRequestValidator {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || !phone.matches("^\\+\\d{1,2}\\d{2,3}9\\d{8}$")) {
            context
                    .buildConstraintViolationWithTemplate("Telefone inválido. O formato deve ser +5511999999999.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
