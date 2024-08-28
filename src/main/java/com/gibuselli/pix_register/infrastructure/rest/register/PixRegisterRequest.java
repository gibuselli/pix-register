package com.gibuselli.pix_register.infrastructure.rest.register;

import com.gibuselli.pix_register.infrastructure.rest.validator.ValidPixKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidPixKey
public class PixRegisterRequest {

    @NotNull(message = "Tipo da chave é obrigatório.")
    @Size(max = 9, message = "Tipo da chave deve ter até 9 caracteres")
    String keyType;

    @NotBlank(message = "Valor da chave é obrigatório.")
    @Size(max = 77, message = "Valor da chave deve ter até 77 caracteres")
    String keyValue;

    @NotNull(message = "Tipo da conta é obrigatório.")
    @Size(max = 10, message = "Tipo da conta deve ter até 10 caracteres")
    String accountType;

    @NotNull(message = "Tipo de cliente é obrigatório.")
    @Size(max = 10, message = "Tipo de cliente deve ter até 10 caracteres")
    String personType;

    @NotBlank(message = "Número da agência é obrigatório.")
    @Size(max = 4, message = "Agência deve ter até 4 caracteres")
    @Pattern(regexp = "\\d+", message = "Agência deve conter apenas números")
    String agency;

    @NotBlank(message = "Número da conta é obrigatório.")
    @Size(max = 8, message = "Número da conta deve ter até 8 caracteres")
    @Pattern(regexp = "\\d+", message = "Conta deve conter apenas números")
    String account;

    @NotBlank(message = "Nome do cliente é obrigatório.")
    @Size(max = 30, message = "Nome do cliente deve ter até 30 caracteres")
    String customerName;

    @Size(max = 45, message = "Sobrenome do cliente deve ter até 45 caracteres")
    String customerLastName;

    public String getKeyType() {
        return keyType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getPersonType() {
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
