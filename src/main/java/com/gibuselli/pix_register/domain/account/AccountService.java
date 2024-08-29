package com.gibuselli.pix_register.domain.account;

import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAndSaveAccount(
            final String name,
            final String lastName,
            final String agency,
            final String accountNumber,
            final AccountType accountType,
            final PersonType personType
    ) {
        final var account = new Account.Builder()
                .name(name)
                .lastName(lastName)
                .agency(agency)
                .accountNumber(accountNumber)
                .accountType(accountType)
                .personType(personType)
                .build();

        return accountRepository.save(account);
    }

    public Account findOrCreateNew(PixRegisterData data) {
        return accountRepository.findByAgencyAndAccountNumber(data.agency(), data.accountNumber())
                .orElseGet(() -> createAndSaveAccount(
                        data.customerName(),
                        data.customerLastName(),
                        data.agency(),
                        data.accountNumber(),
                        data.accountType(),
                        data.personType()
                ));
    }

    @Transactional
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean existsByAgencyAndAccountNumber(String agency, String accountNumber, UUID id) {
        return accountRepository.existsByAgencyAndAccountNumberAndIdNot(agency, accountNumber, id);
    }
}
