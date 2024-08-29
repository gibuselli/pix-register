package com.gibuselli.pix_register.domain.account;

import com.gibuselli.pix_register.domain.pixkey.PixKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    @Id
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String customerName;

    @Column
    private String customerLastName;

    @Column(nullable = false)
    private String agency;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false)
    private PersonType personType;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PixKey> pixKeys = new HashSet<>();

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Account(Builder builder) {
        this.customerName = builder.name;
        this.customerLastName = builder.lastName;
        this.agency = builder.agency;
        this.accountNumber = builder.accountNumber;
        this.accountType = builder.accountType;
        this.personType = builder.personType;
    }

    protected Account() {}

    public boolean isLegalPerson() {
        return PersonType.JURIDICA.equals(personType);
    }

    public boolean isNaturalPerson() {
        return PersonType.FISICA.equals(personType);
    }

    public Set<PixKey> getPixKeys() {
        return pixKeys;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public PersonType getPersonType() {
        return personType;
    }
    
    public void updateAccount(
            final String name,
            final String lastName,
            final String agency,
            final String account
    ) {
        this.customerName = name;
        this.agency = agency;
        this.accountNumber = account;

        if (StringUtils.isNotEmpty(lastName)) {
            this.customerLastName = lastName;
        } else {
            this.setCustomerLastName(null);
        }
        
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addPixKey(PixKey pixKey) {
        this.pixKeys.add(pixKey);
    }

    public static class Builder {
        private String name;
        private String lastName;
        private String agency;
        private String accountNumber;
        private AccountType accountType;
        private PersonType personType;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder agency(String agency) {
            this.agency = agency;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder personType(PersonType personType) {
            this.personType = personType;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
