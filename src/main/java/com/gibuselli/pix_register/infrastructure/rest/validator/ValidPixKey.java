package com.gibuselli.pix_register.infrastructure.rest.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PixRegisterRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPixKey {
    String message() default "Chave Pix inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
