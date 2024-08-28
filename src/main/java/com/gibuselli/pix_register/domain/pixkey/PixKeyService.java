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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PixKeyService {

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

    public UUID registerPixKey(PixRegisterData data) {
        if (pixKeyRepository.existsByTypeAndValue(data.keyType(), data.keyValue())) {
            throw new PixKeyAlreadyExistsException(data.keyType().name(), data.keyValue());
        }

        final var customer = customerService.findOrCreateNew(data);

        try {
            validatorContext.validate(customer, data.keyType(), data.keyValue());
        } catch (Exception ex) {
            throw ex;
        }

        final var pixKey = createAndSavePixKey(customer, data.keyType(), data.keyValue());

        return pixKey.getId();
    }

    public PixKey updatePixKey(PixUpdateData data) {
        final var pixKey = pixKeyRepository.findByIdAndIsEnabled(data.id(), true)
                .orElseThrow(() -> new PixKeyNotFoundException(data.id()));

        final var customer = pixKey.getCustomer();

        customer.setAgency(data.agency());
        customer.setAccount(data.account());
        customer.setName(data.customerName());

        if (StringUtils.isNotEmpty(data.customerLastName())) {
            customer.setLastName(data.customerLastName());
        } else {
            customer.setLastName(null);
        }

        customerService.saveCustomer(customer);

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
    private PixKey savePixKey(PixKey pixKey) {
        return pixKeyRepository.save(pixKey);
    }

    public PixKey disablePixKey(UUID id) {
        final var pixKey = pixKeyRepository.findById(id)
                .orElseThrow(() -> new PixKeyNotFoundException(id));

        if (pixKey.isDisabled()) {
            throw new DisabledPixException(id);
        }

        pixKey.disableKey();

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
