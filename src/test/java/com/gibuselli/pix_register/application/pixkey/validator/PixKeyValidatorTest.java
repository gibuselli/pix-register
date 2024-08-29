package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.AccountType;
import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.account.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.domain.pixkey.PixKey;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PixKeyValidatorTest {

    @Test
    void testLegalPersonValidator_WithExistingCnpjKey_ShouldThrowException() {
        // Arrange
        Account account = new Account.Builder()
                .name("Empresa XYZ")
                .agency("0000")
                .accountType(AccountType.CORRENTE)
                .accountNumber("12345")
                .personType(PersonType.JURIDICA)
                .build();

        PixKey pixKey = new PixKey.Builder()
                .type(KeyType.CNPJ)
                .value("12345678000195")
                .isEnabled(true)
                .account(account)
                .build();

        account.addPixKey(pixKey);

        LegalPersonValidator validator = new LegalPersonValidator();

        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> validator.validate(account, KeyType.CNPJ, "12345678000190"));

        Assertions.assertEquals("Já existe uma chave CNPJ", exception.getMessage());
    }
}
