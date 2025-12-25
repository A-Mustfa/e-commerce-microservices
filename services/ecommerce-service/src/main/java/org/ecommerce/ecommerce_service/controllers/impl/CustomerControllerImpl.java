package org.ecommerce.ecommerce_service.controllers.impl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.controllers.CustomerController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerResponse;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerRequest;
import org.ecommerce.ecommerce_service.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerJWT")
@Tag(name = "Customer", description = "Operations related to customer info")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Long userId){
        customerService.createCustomer(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PatchMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerRequest request, @AuthenticationPrincipal Jwt jwt){
        CustomerResponse response = customerService.updateCustomer(request,jwt);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> showProfile(@AuthenticationPrincipal Jwt jwt){
        CustomerResponse response = customerService.getProfile(jwt.getClaim("userId"));
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<CustomerResponse> customerResponses = customerService.getAllCustomers();
        return ResponseEntity.ok().body(customerResponses);
    }

    @Override
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteCustomer(@PathVariable Long userId){
        customerService.deleteCustomer(userId);
        return ResponseEntity.noContent().build();
    }

}
