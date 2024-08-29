package com.gibuselli.pix_register.infrastructure.rest.validator;

import jakarta.validation.ConstraintValidatorContext;

public interface PixKeyRequestValidator {
    boolean isValid(String keyValue, ConstraintValidatorContext context);
}
