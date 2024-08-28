package com.gibuselli.pix_register.domain.customer;

import com.gibuselli.pix_register.infrastructure.dto.PixRegisterData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createAndSaveCustomer(
            final String name,
            final String lastName,
            final String agency,
            final String account,
            final AccountType accountType,
            final PersonType personType
    ) {
        final var customer = new Customer.Builder()
                .name(name)
                .lastName(lastName)
                .agency(agency)
                .account(account)
                .accountType(accountType)
                .personType(personType)
                .build();

        return customerRepository.save(customer);
    }

    public Customer findOrCreateNew(PixRegisterData data) {
        return customerRepository.findByAgencyAndAccount(data.agency(), data.account())
                .orElseGet(() -> createAndSaveCustomer(
                        data.customerName(),
                        data.customerLastName(),
                        data.agency(),
                        data.account(),
                        data.accountType(),
                        data.personType()
                ));
    }

    @Transactional
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
