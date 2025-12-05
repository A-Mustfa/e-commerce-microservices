package org.ecommerce.customer.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ecommerce.customer.dtos.CustomerResponse;
import org.ecommerce.customer.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.ecommerce.customer.dtos.CustomerRequest;
import org.ecommerce.customer.mappers.CustomerMapper;
import org.ecommerce.customer.models.Customer;
import org.ecommerce.customer.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public Customer createCustomer(CustomerRequest request){
        Customer newCustomer = customerRepository.save(mapper.toCustomer(request));
        return newCustomer;
    }

    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> {throw new CustomerNotFoundException("please validate 'id'");}
                );
        return customer;
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    // TODO PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(Long customerId) {
        Customer customerToDelete = this.findById(customerId);
        customerRepository.delete(customerToDelete);
    }
}
