package org.ecommerce.ecommerce_service.services;

import commons.utils.Exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerRequest;
import org.ecommerce.ecommerce_service.mappers.CustomerMapper;
import org.ecommerce.ecommerce_service.models.Customer;
import org.ecommerce.ecommerce_service.repositories.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public void createCustomer(Long userId){
        customerRepository.save(mapper.toCustomer(userId));
    }

    public Customer getCustomer(Long userId) {
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(
                        () -> {throw new ResourceNotFoundException("please validate 'id'");}
                );
        return customer;
    }

    public CustomerResponse getProfile(Long userId){
        Customer customer = getCustomer(userId);
        return mapper.toCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> customers = customerRepository.findAll().stream().map(mapper::toCustomerResponse)
                .collect(Collectors.toList());;
        return customers;
    }

    public void deleteCustomer(Long userId) {
        Customer customerToDelete = getCustomer(userId);
        customerRepository.delete(customerToDelete);
    }

    public CustomerResponse updateCustomer(@Valid CustomerRequest request, Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        Customer customerToUpdate = getCustomer(userId);
        customerToUpdate.setName(request.getName());
        customerToUpdate.setAddress(request.getAddress());
        customerToUpdate.setPhone(request.getPhone());
        customerRepository.save(customerToUpdate);
        return mapper.toCustomerResponse(customerToUpdate);
    }

}
