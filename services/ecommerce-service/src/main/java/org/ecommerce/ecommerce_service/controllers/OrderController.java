package org.ecommerce.ecommerce_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.ecommerce.ecommerce_service.dto.ErrorResponse;
import org.ecommerce.ecommerce_service.dto.order.OrderResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface OrderController {

    @Operation(
            summary = "Place an order",
            description = "Checkout the current user's cart and create a new order",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Order placed successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OrderResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
                                                    {
                                                        "id": 101,
                                                        "userId": 5,
                                                        "orderDeliveryAddress": "Cairo, Egypt",
                                                        "customerPhone": "01023193078",
                                                        "orderTotalAmount": 500.00,
                                                        "orderStatus": "PENDING",
                                                        "orderItems": [
                                                            {
                                                                "id": 1,
                                                                "itemId": 5,
                                                                "name": "PlayStation",
                                                                "quantity": 2,
                                                                "unitPrice": 250.00,
                                                                "totalPrice": 500.00
                                                            }
                                                        ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cart is empty or user not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Stock issues or invalid data",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping
    ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "Cancel an order",
            description = "Cancel an existing order by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order canceled successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OrderResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @DeleteMapping("/{orderId}")
    ResponseEntity<OrderResponse> cancelOrder(@Parameter(description = "ID of the order to cancel") @PathVariable Long orderId);

    @Operation(
            summary = "Get order details",
            description = "Retrieve details of a specific order by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order retrieved successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OrderResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse> getOrder(@Parameter(description = "ID of the order to retrieve") @PathVariable Long orderId);

    @Operation(
            summary = "Get user order history",
            description = "Retrieve all orders placed by the authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of orders retrieved",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OrderResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal Jwt jwt);
}
