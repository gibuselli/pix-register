package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.account.AccountType;
import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.account.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.domain.pixkey.PixKey;
import com.gibuselli.pix_register.infrastructure.exception.CnpjOrCpfAlreadyRegistered;
import com.gibuselli.pix_register.infrastructure.exception.InvalidKeyForNaturalPersonException;
import com.gibuselli.pix_register.infrastructure.exception.MaxKeysException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PixKeyValidatorTest {

    private Account naturalPersonAccount;
    private Account legalPersonAccount;

    @BeforeEach
    public void setUp() {
        naturalPersonAccount = new Account.Builder()
                .name("Fulano")
                .agency("0000")
                .accountType(AccountType.CORRENTE)
                .accountNumber("12345")
                .personType(PersonType.FISICA)
                .build();

        legalPersonAccount = new Account.Builder()
                .name("Empresa XYZ")
                .agency("0000")
                .accountType(AccountType.CORRENTE)
                .accountNumber("12345")
                .personType(PersonType.JURIDICA)
                .build();
    }

    @Test
    public void should_throw_exception_when_cnpj_key_for_natural_person() {
        PixKeyValidator validator = new NaturalPersonValidator();

        assertThrows(InvalidKeyForNaturalPersonException.class,
                () -> validator.validate(naturalPersonAccount, KeyType.CNPJ, "12345678000199"));
    }

    @Test
    public void should_throw_exception_when_cpf_key_exists() {
        PixKeyValidator validator = new NaturalPersonValidator();

        naturalPersonAccount.addPixKey(
                new PixKey.Builder()
                        .type(KeyType.CPF)
                        .value("12345678900")
                        .account(naturalPersonAccount)
                        .isEnabled(true)
                        .build());

        assertThrows(CnpjOrCpfAlreadyRegistered.class,
                () -> validator.validate(naturalPersonAccount, KeyType.CPF, "987.654.321-00"));
    }

    @Test
    public void should_throw_exception_when_cnpj_key_exists() {
        PixKeyValidator validator = new LegalPersonValidator();

        legalPersonAccount.addPixKey(
                new PixKey.Builder()
                        .type(KeyType.CNPJ)
                        .value("12345678000199")
                        .account(legalPersonAccount)
                        .isEnabled(true)
                        .build());

        assertThrows(CnpjOrCpfAlreadyRegistered.class,
                () -> validator.validate(legalPersonAccount, KeyType.CNPJ, "98.765.432/0001-00"));
    }

    @Test
    public void should_throw_exception_when_max_keys_for_natural_person() {
        PixKeyValidator validator = new MaxKeysValidator();

        for (int i = 0; i < 5; i++) {
            naturalPersonAccount.addPixKey(
                    new PixKey.Builder()
                            .type(KeyType.EMAIL)
                            .value("email" + i + "@domain.com")
                            .account(naturalPersonAccount)
                            .isEnabled(true)
                            .build());
        }

        assertThrows(MaxKeysException.class,
                () -> validator.validate(naturalPersonAccount, KeyType.EMAIL, "anotheremail@domain.com"));
    }

    @Test
    public void should_throw_exception_when_max_keys_for_legal_person() {
        PixKeyValidator validator = new MaxKeysValidator();

        for (int i = 0; i < 20; i++) {
            legalPersonAccount.addPixKey(
                    new PixKey.Builder()
                            .type(KeyType.EMAIL)
                            .value("email" + i + "@domain.com")
                            .account(legalPersonAccount)
                            .isEnabled(true)
                            .build());
        }

        assertThrows(MaxKeysException.class,
                () -> validator.validate(legalPersonAccount, KeyType.EMAIL, "anotheremail@domain.com"));
    }

    @Test
    public void should_validate_when_cpf_key_is_valid() {
        PixKeyValidator validator = new NaturalPersonValidator();

        assertDoesNotThrow(() -> validator.validate(naturalPersonAccount, KeyType.CPF, "12345678900"));
    }

    @Test
    public void should_validate_when_cnpj_key_is_valid() {
        PixKeyValidator validator = new LegalPersonValidator();

        assertDoesNotThrow(() -> validator.validate(legalPersonAccount, KeyType.CNPJ, "12345678000199"));
    }
}
