package com.gibuselli.pix_register.infrastructure.rest.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PixUpdateRequest {

    @NotNull(message = "Tipo da conta é obrigatório.")
    @Size(max = 10, message = "Tipo da conta deve ter até 10 caracteres")
    String accountType;

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

    public String getAccountType() {
        return accountType;
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
