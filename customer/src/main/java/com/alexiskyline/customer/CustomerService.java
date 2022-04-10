package com.alexiskyline.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService() {
    public void registerCustomer( CustomerRegistrationRequest request ) {
        Customer customer = Customer.builder()
                .firstName( request.fistName() )
                .lastName( request.lastName() )
                .email( request.email() )
                .build();
    }
}