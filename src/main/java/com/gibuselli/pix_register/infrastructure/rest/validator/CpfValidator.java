package com.gibuselli.pix_register.infrastructure.rest.validator;

import com.gibuselli.pix_register.infrastructure.util.CpfValidUtil;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements PixKeyRequestValidator {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || !cpf.matches("\\d{11}") || !CpfValidUtil.isCpfValid(cpf)) {
            context
                    .buildConstraintViolationWithTemplate("CPF inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
