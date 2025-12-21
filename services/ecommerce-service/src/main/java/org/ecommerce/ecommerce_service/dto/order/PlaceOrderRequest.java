//package org.ecommerce.ecommerce_service.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class PlaceOrderRequest {
//    @NotNull(message = "Cart ID is required")
//    private Long cartId;
//
//    @NotBlank(message = "Delivery address is required")
//    @Size(min = 10, max = 500, message = "Address must be between 10 and 500 characters")
//    private String deliveryAddress;
//
//    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
//    private String customerPhone;
//}
