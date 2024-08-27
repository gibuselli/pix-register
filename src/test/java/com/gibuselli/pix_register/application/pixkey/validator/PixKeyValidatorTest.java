package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.AccountType;
import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.customer.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.domain.pixkey.PixKey;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PixKeyValidatorTest {

    @Test
    void testLegalPersonValidator_WithExistingCnpjKey_ShouldThrowException() {
        // Arrange
        Customer customer = new Customer.Builder()
                .name("Empresa XYZ")
                .agency("0000")
                .accountType(AccountType.CHECKING_ACCOUNT)
                .account("12345")
                .personType(PersonType.LEGAL)
                .build();

        PixKey pixKey = new PixKey.Builder()
                .type(KeyType.CNPJ)
                .value("12345678000195")
                .isEnabled(true)
                .customer(customer)
                .build();

        customer.addPixKey(pixKey);

        LegalPersonValidator validator = new LegalPersonValidator();

        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> validator.validate(customer, KeyType.CNPJ, "12345678000190"));

        Assertions.assertEquals("Já existe uma chave CNPJ", exception.getMessage());
    }
}
