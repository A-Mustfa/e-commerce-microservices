package org.ecommerce.ecommerce_service.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerRequest;
import org.ecommerce.ecommerce_service.exceptions.CustomerNotFoundException;
import org.ecommerce.ecommerce_service.mappers.CustomerMapper;
import org.ecommerce.ecommerce_service.models.Customer;
import org.ecommerce.ecommerce_service.repositories.CustomerRepository;

import java.util.List;

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
                        () -> {throw new CustomerNotFoundException("please validate 'id'");}
                );
        return customer;
    }

    public Customer getProfile(Long userId){
        Customer customer = getCustomer(userId);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public void deleteCustomer(Long userId) {
        Customer customerToDelete = getCustomer(userId);
        customerRepository.delete(customerToDelete);
    }

    public Customer updateCustomer(@Valid CustomerRequest request, Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        Customer customerToUpdate = getCustomer(userId);
        customerToUpdate.setName(request.getName());
        customerToUpdate.setAddress(request.getAddress());
        customerToUpdate.setPhone(request.getPhone());
        return customerRepository.save(customerToUpdate);
    }

}
