package com.alexiskyline.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer( CustomerRegistrationRequest request ) {
        Customer customer = Customer.builder()
                .firstName( request.fistName() )
                .lastName( request.lastName() )
                .email( request.email() )
                .build();
        // Todo: check if email valid
        // Todo: check if email not token
        customerRepository.saveAndFlush( customer );
        // Todo check if fraud
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if( fraudCheckResponse.isFraudster() ) {
            throw  new IllegalStateException( "fraudster" );
        }
        // Todo: send notification
    }
}