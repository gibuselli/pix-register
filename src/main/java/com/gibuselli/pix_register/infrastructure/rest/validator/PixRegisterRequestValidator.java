package com.gibuselli.pix_register.infrastructure.rest.validator;

import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.rest.register.PixRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class PixRegisterRequestValidator implements ConstraintValidator<ValidPixKey, PixRegisterRequest> {

    private final Map<KeyType, PixKeyRequestValidator> validators = new HashMap<>();

    public PixRegisterRequestValidator() {
        validators.put(KeyType.PHONE, new PhoneValidator());
        validators.put(KeyType.EMAIL, new EmailValidator());
        validators.put(KeyType.CPF, new CpfValidator());
        validators.put(KeyType.CNPJ, new CnpjValidator());
        validators.put(KeyType.RANDOM, new RandomKeyValidator());
    }

    @Override
    public boolean isValid(PixRegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
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

        PixKeyRequestValidator validator = validators.get(keyType);
        return validator != null && validator.isValid(keyValue, context);
    }
}
