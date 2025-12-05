package org.ecommerce.customer.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.ecommerce.customer.models.Customer;

import java.time.OffsetDateTime;

public record CustomerRequest(
        String name,
        @Email
        @NotBlank(message = "enter the email please")
        String email,
        @NotBlank(message = "enter the password pleas")
        @Size(min = 8, max = 16)
        String password,
        @NotNull(message = "you had to specify a role: 'CUSTOMER' , 'ADMIN' ")
        Customer.Role role,
        String phone,
        @NotBlank(message = "address field is required")
        String address)
{

}
