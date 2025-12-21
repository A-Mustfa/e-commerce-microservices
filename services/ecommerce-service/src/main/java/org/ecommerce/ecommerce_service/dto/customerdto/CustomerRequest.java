package org.ecommerce.ecommerce_service.dto.customerdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotEmpty(message = "please enter your name")
    private String name;
    @NotBlank(message = "enter your address")
    private String address;
    @NotNull(message = "enter your phone")
    private String phone;
}
