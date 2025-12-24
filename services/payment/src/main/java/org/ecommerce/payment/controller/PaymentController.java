package org.ecommerce.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface PaymentController {

    @Operation(
            summary = "Process a payment",
            description = "Initiate a purchase transaction for a specific order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment details including user, order, and amount",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentRequest.class),
                            examples = @ExampleObject(
                                    name = "Payment Request",
                                    value = """
                                            {
                                              "userId": 5,
                                              "orderId": 101,
                                              "amount": 250.50
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Payment processed successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PaymentResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "paymentId": 88,
                                                      "customerId": 5,
                                                      "status": "APPROVED"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid payment request (e.g., negative amount, missing fields)",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Payment processing failed",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PostMapping("/purchase")
    ResponseEntity<PaymentResponse> purchase(@Valid @RequestBody PaymentRequest paymentRequest) throws IOException;

    @Operation(
            summary = "List all payments",
            description = "Retrieve a history of all payment transactions (Admin Only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of payments retrieved",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PaymentResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied (Requires ADMIN role)",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<PaymentResponse>> listPayments();
}
