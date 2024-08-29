package com.gibuselli.pix_register.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAgencyAndAccountNumber(String agency, String accountNumber);

    boolean existsByAgencyAndAccountNumberAndIdNot(String agency, String accountNumber, UUID id);
}
