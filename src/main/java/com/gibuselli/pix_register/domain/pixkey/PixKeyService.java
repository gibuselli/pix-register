package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.application.pixkey.validator.*;
import com.gibuselli.pix_register.domain.account.Account;
import com.gibuselli.pix_register.domain.account.AccountService;
import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import com.gibuselli.pix_register.infrastructure.dto.PixSearchParams;
import com.gibuselli.pix_register.infrastructure.dto.PixUpdateData;
import com.gibuselli.pix_register.infrastructure.exception.AccountAlreadyExistsException;
import com.gibuselli.pix_register.infrastructure.exception.DisabledPixException;
import com.gibuselli.pix_register.infrastructure.exception.PixKeyAlreadyExistsException;
import com.gibuselli.pix_register.infrastructure.exception.PixKeyNotFoundException;
import com.gibuselli.pix_register.infrastructure.logger.CustomLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PixKeyService {

    private static final CustomLogger log = CustomLogger.getLogger(PixKeyService.class);

    private final PixKeyRepository pixKeyRepository;

    private final AccountService accountService;

    private final PixKeyValidatorContext validatorContext;

    public PixKeyService(PixKeyRepository pixKeyRepository, AccountService accountService) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountService = accountService;

        validatorContext = new PixKeyValidatorContext();
        validatorContext.add(new MaxKeysValidator());
        validatorContext.add(new NaturalPersonValidator());
        validatorContext.add(new LegalPersonValidator());
    }

    @Transactional
    public UUID registerPixKey(PixRegisterData data) {
        if (pixKeyRepository.existsByTypeAndValue(data.keyType(), data.keyValue())) {
            throw new PixKeyAlreadyExistsException(data.keyType().name(), data.keyValue());
        }

        final var customer = accountService.findOrCreateNew(data);

        try {
            validatorContext.validate(customer, data.keyType(), data.keyValue());
        } catch (Exception ex) {
            log.tag("agency", customer.getAgency())
                    .tag("accountNumber", customer.getAccountNumber())
                    .tag("keyType", data.keyType().name())
                    .tag("keyValue", data.keyValue())
                    .warn(ex.getMessage());

            throw ex;
        }

        final var pixKey = createAndSavePixKey(customer, data.keyType(), data.keyValue());

        log.tag("name", customer.getCustomerName())
                .tag("lastName", customer.getCustomerLastName())
                .tag("personType", customer.getPersonType().name())
                .tag("agency", customer.getAgency())
                .tag("accountNumber", customer.getAccountNumber())
                .tag("accountType", customer.getAccountType().name())
                .tag("accountNumber", customer.getAccountNumber())
                .tag("keyType", pixKey.getType().name())
                .tag("keyValue", pixKey.getValue())
                .info("pix_key_created");

        return pixKey.getId();
    }

    @Transactional
    public PixKey updatePixKey(PixUpdateData data) {
        final var pixKey = pixKeyRepository.findByIdAndIsEnabled(data.id(), true)
                .orElseThrow(() -> new PixKeyNotFoundException(data.id()));

        if (accountService.existsByAgencyAndAccountNumber(data.agency(), data.accountNumber())) {
            throw new AccountAlreadyExistsException(data.agency(), data.accountNumber());
        }

        final var customer = pixKey.getCustomer();

        customer.updateAccount(
                data.customerName(),
                data.customerLastName(),
                data.agency(),
                data.accountNumber()
        );

        accountService.saveAccount(customer);

        log.tag("name", customer.getCustomerName())
                .tag("lastName", customer.getCustomerLastName())
                .tag("personType", customer.getPersonType().name())
                .tag("agency", customer.getAgency())
                .tag("accountNumber", customer.getAccountNumber())
                .tag("accountType", customer.getAccountType().name())
                .tag("accountNumber", customer.getAccountNumber())
                .tag("keyType", pixKey.getType().name())
                .tag("keyValue", pixKey.getValue())
                .info("pix_key_updated");

        return pixKey;
    }

    @Transactional()
    private PixKey createAndSavePixKey(
            final Account account,
            final KeyType keyType,
            final String keyValue) {

        final var pixKey =
                new PixKey.Builder()
                        .account(account)
                        .type(keyType)
                        .value(keyValue)
                        .isEnabled(true)
                        .build();

        return pixKeyRepository.save(pixKey);
    }

    @Transactional
    public PixKey disablePixKey(UUID id) {
        final var pixKey = pixKeyRepository.findById(id)
                .orElseThrow(() -> new PixKeyNotFoundException(id));

        if (pixKey.isDisabled()) {
            throw new DisabledPixException(id);
        }

        pixKey.disableKey();

        log.tag("keyType", pixKey.getType().name())
                .tag("keyValue", pixKey.getValue())
                .info("pix_key_disabled");

        return pixKeyRepository.save(pixKey);
    }

    public PixKey searchById(UUID id) {
        return pixKeyRepository.findById(id)
                .orElseThrow(() -> new PixKeyNotFoundException(id));
    }

    public List<PixKey> searchByParams(PixSearchParams params) {
        return pixKeyRepository.queryByParams(
                params.keyType(),
                params.agency(),
                params.accountNumber(),
                params.customerName(),
                params.createdAt(),
                params.disabledAt()
        );
    }
}
