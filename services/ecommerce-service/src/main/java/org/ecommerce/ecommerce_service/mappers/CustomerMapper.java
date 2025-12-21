package org.ecommerce.ecommerce_service.mappers;

import org.springframework.stereotype.Service;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerResponse;
import org.ecommerce.ecommerce_service.models.Customer;

@Service
public class CustomerMapper {

    public Customer toCustomer(Long userId){
        return Customer.builder()
                .userId(userId)
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .userId(customer.getUserId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }
}
