package org.ecommerce.ecommerce_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerResponse;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerRequest;
import org.ecommerce.ecommerce_service.mappers.CustomerMapper;
import org.ecommerce.ecommerce_service.services.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Long userId){
        customerService.createCustomer(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerRequest request, @AuthenticationPrincipal Jwt jwt){
        CustomerResponse response = mapper.toCustomerResponse(customerService.updateCustomer(request,jwt));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> showProfile(@AuthenticationPrincipal Jwt jwt){
        CustomerResponse response = mapper.toCustomerResponse(customerService.getProfile(jwt.getClaim("userId")));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<CustomerResponse> customerResponses = customerService.getAllCustomers().stream().map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(customerResponses);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long userId){
        customerService.deleteCustomer(userId);
        return ResponseEntity.noContent().build();
    }

}
