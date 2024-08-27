package com.gibuselli.pix_register.infrastructure.rest;

import com.gibuselli.pix_register.domain.customer.AccountType;
import com.gibuselli.pix_register.domain.customer.PersonType;
import com.gibuselli.pix_register.domain.pixkey.KeyType;
import com.gibuselli.pix_register.infrastructure.rest.validator.ValidPixKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ValidPixKey
public class PixRegisterRequest {

    @NotNull(message = "Tipo da chave é obrigatório.")
    KeyType keyType;

    @NotBlank(message = "Valor da chave é obrigatório.")
    @Size(max = 77, message = "Valor da chave deve ter até 77 caracteres")
    String keyValue;

    @NotNull(message = "Tipo da conta é obrigatório.")
    @Size(max = 10, message = "Tipo da conta deve ter até 10 caracteres")
    AccountType accountType;

    @NotNull(message = "Tipo de cliente é obrigatório.")
    @Size(max = 10, message = "Tipo de cliente deve ter até 10 caracteres")
    PersonType personType;

    @NotBlank(message = "Número da agência é obrigatório.")
    @Size(max = 4, message = "Agência deve ter até 4 caracteres")
    String agency;

    @NotBlank(message = "Número da conta é obrigatório.")
    @Size(max = 8, message = "Número da conta deve ter até 8 caracteres")
    String account;

    @NotBlank(message = "Nome do cliente é obrigatório.")
    @Size(max = 30, message = "Nome do cliente deve ter até 30 caracteres")
    String customerName;

    @Size(max = 45, message = "Sobrenome do cliente deve ter até 45 caracteres")
    String customerLastName;

    public KeyType getKeyType() {
        return keyType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccount() {
        return account;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }
}
