package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.application.pixkey.validator.*;
import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.customer.CustomerService;
import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import com.gibuselli.pix_register.infrastructure.exception.PixKeyAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (pixKeyRepository.existsByKeyTypeAndKeyValue(data.keyType(), data.keyValue())) {
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

    @Transactional
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
}
