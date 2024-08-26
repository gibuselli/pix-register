package com.gibuselli.pix_register.application.pixkey.validator;

import com.gibuselli.pix_register.domain.customer.Customer;
import com.gibuselli.pix_register.domain.pixkey.KeyType;

public interface PixKeyValidator {
    void validate(Customer customer, KeyType keyType, String keyValue);
}
