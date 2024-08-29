package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.domain.account.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "PIX_KEY",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ACCOUNT_ID", "TYPE", "VALUE"})})
public class PixKey {

    @Id
    @Column(name = "PIX_KEY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KeyType type;

    @Column(nullable = false)
    private String value;

    @Column(name = "IS_ENABLED", nullable = false)
    private boolean isEnabled;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DISABLED_AT")
    private LocalDateTime disabledAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public PixKey(Builder builder) {
        this.account = builder.account;
        this.type = builder.type;
        this.value = builder.value;
        this.isEnabled = builder.isEnabled;
    }

    protected PixKey() {}

    public UUID getId() {
        return id;
    }

    public KeyType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isDisabled() {
        return !isEnabled;
    }

    public void disableKey() {
        isEnabled = false;
        disabledAt = LocalDateTime.now();
    }

    public Account getCustomer() {
        return account;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDisabledAt() {
        return disabledAt;
    }

    public static class Builder {
        private Account account;
        private KeyType type;
        private String value;
        private boolean isEnabled;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder type(KeyType type) {
            this.type = type;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder isEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public PixKey build() {
            return new PixKey(this);
        }
    }
}
