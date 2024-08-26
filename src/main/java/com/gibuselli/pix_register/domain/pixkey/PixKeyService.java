package com.gibuselli.pix_register.domain.pixkey;

import com.gibuselli.pix_register.application.pixkey.validator.*;
import com.gibuselli.pix_register.domain.customer.CustomerService;
import org.springframework.stereotype.Service;

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

    public void registerPixKey(UUID customerId, KeyType keyType, String keyValue) {
        if (pixKeyRepository.existsByKeyTypeAndKeyValue(keyType, keyValue)) {
            throw new RuntimeException("asdas");
        }

        final var customer = customerService.findCustomerById(customerId);

        try {
            validatorContext.validate(customer, keyType, keyValue);
        } catch (Exception ex) {
            throw ex;
        }

        final var pixKey =
                new PixKey.Builder()
                        .customer(customer)
                        .type(keyType)
                        .value(keyValue)
                        .isEnabled(true)
                        .build();

    }
}
