package com.gibuselli.pix_register.infrastructure.rest.validator;

import com.gibuselli.pix_register.infrastructure.util.CnpjValidUtil;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidator implements PixKeyRequestValidator {
    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || !CnpjValidUtil.isCnpjValid(cnpj)) {
            context
                    .buildConstraintViolationWithTemplate("CNPJ inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
