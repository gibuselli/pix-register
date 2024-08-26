package com.gibuselli.pix_register.domain.customer;

import com.gibuselli.pix_register.domain.pixkey.KeyType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public long countKeysByCustomer(Customer customer) {
        return 0;
    }

    public long countKeysByCustomerAndKeyType(Customer customer, KeyType keyType) {
        return 0;
    }
}
