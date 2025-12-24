package org.ecommerce.ecommerce_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.ecommerce.ecommerce_service.dto.ErrorResponse;
import org.ecommerce.ecommerce_service.dto.cart.CartResponse;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerRequest;
import org.ecommerce.ecommerce_service.dto.customerdto.CustomerResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CustomerController {

    @Operation(
            summary = "Create a new customer",
            description = "Create a customer using the provided userId",
            parameters = {
        @Parameter(
                name = "userId",
                description = "ID of the user to create a customer profile for",
                required = true,
                in = ParameterIn.QUERY,
                schema = @Schema(type = "integer", example = "5")
        )
    },
    responses = {
        @ApiResponse(
                responseCode = "201",
                description = "Customer created successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = CustomerResponse.class),
                        examples = @ExampleObject(
                                name = "Success Response",
                                value = """
                                                    {
                                                      "id": 13,
                                                      "name": "Ahmed",
                                                      "phone": "01023193078"
                                                    }
                                                    """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                                    {
                                                      "timestamp": "2025-12-23T14:05:00.000Z",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "user not found",
                                                      "path": "/api/v1/customer"
                                                    }
                                                    """
                        )
                )
        )
    }
    )
    @PostMapping("/create")
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody Long userId);

    @Operation(
            summary = "Update customer information",
            description = "Update the current authenticated customer's profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer update request body",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer updated successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PatchMapping("/update")
    ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerRequest request, @AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "user profile",
            description = "get user profile using credentials",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user profile",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value =
                                                """
                                                    {
                                                    "id": 13,
                                                     "name": "cairo",
                                                     "phone": "01023193078"
                                                     }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "user not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2025-12-23T14:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "user not found",
                                                        "path": "/api/v1/customer/profile"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/profile")
    ResponseEntity<CustomerResponse> showProfile(@AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "Get all customers",
            description = "Retrieve a list of all customers (admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of customers retrieved",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerResponse.class)
                            )
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<CustomerResponse>> getAllCustomers();

    @Operation(
            summary = "Delete a customer",
            description = "Delete a customer by their userId (admin only)",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID of the customer to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "13")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Customer deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity deleteCustomer(@PathVariable Long userId);
}
