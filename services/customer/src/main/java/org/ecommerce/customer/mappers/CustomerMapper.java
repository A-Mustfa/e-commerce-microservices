package org.ecommerce.customer.mappers;

import org.springframework.stereotype.Service;
import org.ecommerce.customer.dtos.CustomerRequest;
import org.ecommerce.customer.dtos.CustomerResponse;
import org.ecommerce.customer.models.Customer;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request){
        return Customer.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .phone(request.phone())
                .address(request.address())
                .build();
    }
    public CustomerResponse toCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }
}
