package com.gibuselli.pix_register.infrastructure.rest.validator;

import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterRequest;
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
        if (phone == null || !phone.matches("^\\+\\d{1,2}\\d{2,3}9\\d{8}$")) {
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
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }

        int firstVerifier = 11 - (sum % 11);

        if (firstVerifier == 10 || firstVerifier == 11) {
            firstVerifier = 0;
        }

        if (firstVerifier != (cpf.charAt(9) - '0')) {
            return false;
        }

        sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }

        int secondVerifier = 11 - (sum % 11);

        if (secondVerifier == 10 || secondVerifier == 11) {
            secondVerifier = 0;
        }

        return secondVerifier == (cpf.charAt(10) - '0');
    }

    private boolean isCnpjValid(String cnpj) {
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999")) {

            return false;
        }

        int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;

        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }

        int firstVerifier = sum % 11;
        firstVerifier = firstVerifier < 2 ? 0 : 11 - firstVerifier;

        if (firstVerifier != (cnpj.charAt(12) - '0')) {
            return false;
        }

        sum = 0;

        int[] newWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * newWeights[i];
        }

        int secondVerifier = sum % 11;
        secondVerifier = secondVerifier < 2 ? 0 : 11 - secondVerifier;

        return secondVerifier == (cnpj.charAt(13) - '0');
    }
}
