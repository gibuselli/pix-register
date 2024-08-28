package com.gibuselli.pix_register.domain.customer;

import com.gibuselli.pix_register.domain.pixkey.PixKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false)
    private String account;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", nullable = false)
    private PersonType personType;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Customer(Builder builder) {
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.agency = builder.agency;
        this.account = builder.account;
        this.accountType = builder.accountType;
        this.personType = builder.personType;
    }

    protected Customer() {}

    public boolean isLegalPerson() {
        return PersonType.JURIDICA.equals(personType);
    }

    public boolean isNaturalPerson() {
        return PersonType.FISICA.equals(personType);
    }

    public Set<PixKey> getPixKeys() {
        return pixKeys;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccount() {
        return account;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public PersonType getPersonType() {
        return personType;
    }
    
    public void updateCustomer(
            final String name,
            final String lastName,
            final String agency,
            final String account
    ) {
        this.name = name;
        this.agency = agency;
        this.account = account;

        if (StringUtils.isNotEmpty(lastName)) {
            this.lastName = lastName;
        } else {
            this.setLastName(null);
        }
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setAccount(String account) {
        this.account = account;
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
        private String account;
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

        public Builder account(String account) {
            this.account = account;
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

        public Customer build() {
            return new Customer(this);
        }
    }
}
