package org.ecommerce.customer.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.customer.dtos.CustomerRequest;
import org.ecommerce.customer.dtos.CustomerResponse;
import org.ecommerce.customer.mappers.CustomerMapper;
import org.ecommerce.customer.models.Customer;
import org.ecommerce.customer.services.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request){
        Customer newCustomer =customerService.createCustomer(request);
        CustomerResponse response = mapper.toCustomerResponse(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id){
        CustomerResponse response = mapper.toCustomerResponse(customerService.findById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<CustomerResponse> customerResponses =customerService.getAllCustomer().stream().map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
