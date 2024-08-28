package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.application.pixkey.validator.*;
import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.customer.CustomerService;
import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import com.gibuselli.pix_register.infrastructure.dto.PixSearchParams;
import com.gibuselli.pix_register.infrastructure.dto.PixUpdateData;
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

    private final CustomerService customerService;

    private final PixKeyValidatorContext validatorContext;

    public PixKeyService(PixKeyRepository pixKeyRepository, CustomerService customerService) {
        this.pixKeyRepository = pixKeyRepository;
        this.customerService = customerService;

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

        final var customer = customerService.findOrCreateNew(data);

        try {
            validatorContext.validate(customer, data.keyType(), data.keyValue());
        } catch (Exception ex) {
            log.tag("agency", customer.getAgency())
                    .tag("account", customer.getAccount())
                    .tag("keyType", data.keyType().name())
                    .tag("keyValue", data.keyValue())
                    .error(ex.getMessage());

            throw ex;
        }

        final var pixKey = createAndSavePixKey(customer, data.keyType(), data.keyValue());

        log.tag("name", customer.getName())
                .tag("lastName", customer.getLastName())
                .tag("personType", customer.getPersonType().name())
                .tag("agency", customer.getAgency())
                .tag("account", customer.getAccount())
                .tag("accountType", customer.getAccountType().name())
                .tag("account", customer.getAccount())
                .tag("keyType", pixKey.getType().name())
                .tag("keyValue", pixKey.getValue())
                .info("pix_key_created");

        return pixKey.getId();
    }

    @Transactional
    public PixKey updatePixKey(PixUpdateData data) {
        final var pixKey = pixKeyRepository.findByIdAndIsEnabled(data.id(), true)
                .orElseThrow(() -> new PixKeyNotFoundException(data.id()));

        final var customer = pixKey.getCustomer();

        customer.updateCustomer(
                data.customerName(),
                data.customerLastName(),
                data.agency(),
                data.account()
        );

        customerService.saveCustomer(customer);

        log.tag("name", customer.getName())
                .tag("lastName", customer.getLastName())
                .tag("personType", customer.getPersonType().name())
                .tag("agency", customer.getAgency())
                .tag("account", customer.getAccount())
                .tag("accountType", customer.getAccountType().name())
                .tag("account", customer.getAccount())
                .tag("keyType", pixKey.getType().name())
                .tag("keyValue", pixKey.getValue())
                .info("pix_key_updated");

        return pixKey;
    }

    @Transactional()
    private PixKey createAndSavePixKey(
            final Customer customer,
            final KeyType keyType,
            final String keyValue) {

        final var pixKey =
                new PixKey.Builder()
                        .customer(customer)
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
                params.account(),
                params.customerName(),
                params.createdAt(),
                params.disabledAt()
        );
    }
}
