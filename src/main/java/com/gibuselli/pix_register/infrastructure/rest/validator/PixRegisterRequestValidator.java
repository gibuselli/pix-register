package com.gibuselli.pix_register.infrastructure.rest.validator;

import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.rest.PixRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PixRegisterRequestValidator implements ConstraintValidator<ValidPixKey, PixRegisterRequest> {

    @Override
    public boolean isValid(PixRegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true; // Validação para objeto nulo é feita em outro lugar
        }

        String keyValue;
        KeyType keyType;

         try {
             keyValue = request.getKeyValue();
             keyType = KeyType.fromValue(request.getKeyType());
         } catch (Exception ex) {
             context
                     .buildConstraintViolationWithTemplate(ex.getMessage())
                     .addConstraintViolation();

             return false;
         }

        return switch (keyType) {
            case PHONE -> validatePhone(keyValue, context);
            case EMAIL -> validateEmail(keyValue, context);
            case CPF -> validateCpf(keyValue, context);
            case CNPJ -> validateCnpj(keyValue, context);
            case RANDOM -> validateRandomKey(keyValue, context);
        };
    }

    private boolean validatePhone(String phone, ConstraintValidatorContext context) {
        if (phone == null || !phone.matches("\\+55\\d{10,12}")) {
            context
                    .buildConstraintViolationWithTemplate("Telefone inválido. O formato deve ser +5511999999999.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

    private boolean validateEmail(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            context
                    .buildConstraintViolationWithTemplate("Email inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

    private boolean validateCpf(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || !cpf.matches("\\d{11}") || !isCpfValid(cpf)) {
            context
                    .buildConstraintViolationWithTemplate("CPF inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

    private boolean validateCnpj(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || !isCnpjValid(cnpj)) {
            context
                    .buildConstraintViolationWithTemplate("CNPJ inválido.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

    private boolean validateRandomKey(String randomKey, ConstraintValidatorContext context) {
        if (randomKey == null || !randomKey.matches("[a-zA-Z0-9]{1,36}")) {
            context
                    .buildConstraintViolationWithTemplate("Chave aleatória inválida. Deve conter no máximo 36 caracteres alfanuméricos.")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

    private boolean isCpfValid(String cpf) {
        // Implementar lógica de validação de CPF
        return true;
    }

    private boolean isCnpjValid(String cnpj) {
        // Implementar lógica de validação de CNPJ
        return true;
    }
}
