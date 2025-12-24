package org.ecommerce.ecommerce_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.ecommerce.ecommerce_service.dto.ErrorResponse;
import org.ecommerce.ecommerce_service.dto.cart.CartResponse;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

public interface CartController {

    @Operation(
            summary = "get user cart",
            description = "get user cart using credentials",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user cart",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CartResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
                                                {
                                                    "cartId": 13,
                                                    "userId": 11,
                                                    "createdAt": "2025-12-23T13:06:03.990057+02:00",
                                                    "items": [
                                                        {
                                                            "id": 12,
                                                            "itemId": 5,
                                                            "itemName": "SilencePlus Wireless Headphones",
                                                            "quantity": 2,
                                                            "unitPrice": 3999.50,
                                                            "totalPrice": 7999.00
                                                        }
                                                    ],
                                                    "totalPrice": 7999.00,
                                                    "totalQuantity": 2
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "cart not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2025-12-23T14:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "Cart not found",
                                                        "path": "/api/v1/cart/my-cart"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/my-cart")
    ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "clear user cart",
            description = "clear user cart using credentials"
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cleared successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2025-12-23T14:00:00.000Z",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "Invalid or missing token",
                                                "path": "/api/v1/cart/my-cart"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "cart not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                                    {
                                                        "timestamp": "2025-12-23T14:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "Cart not found",
                                                        "path": "/api/v1/cart/my-cart"
                                                    }
                                                    """
                            )
                    )
            )
    })
    @DeleteMapping("/my-cart")
    ResponseEntity<CartResponse> clearCart(@AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "add cart item",
            description = "add item to user cart",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "cart request body",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartItemRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "item added successfully",
                            content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = CartResponse.class),
                                examples = @ExampleObject(
                                    value = """
                                                {
                                                  "cartId": 13,
                                                  "userId": 11,
                                                  "createdAt": "2025-12-23T13:06:03.990057+02:00",
                                                  "items": [
                                                      {
                                                          "id": 12,
                                                          "itemId": 5,
                                                          "itemName": "SilencePlus Wireless Headphones",
                                                          "quantity": 2,
                                                          "unitPrice": 3999.50,
                                                          "totalPrice": 7999.00
                                                      }
                                                  ],
                                                  "totalPrice": 7999.00,
                                                  "totalQuantity": 2
                                                }
                                            """
                                )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                        {
                                                            "timestamp": "2025-12-23T14:00:00.000Z",
                                                            "status": 401,
                                                            "error": "Unauthorized",
                                                            "message": "Invalid or missing token",
                                                            "path": "/api/v1/cart/item"
                                                        }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "cart not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2025-12-23T14:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "Cart not found",
                                                        "path": "/api/v1/cart/my-cart"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/item")
    ResponseEntity<CartResponse> addItemToCart(@Valid @RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal Jwt jwt);

    @Operation(
            summary = "remove cart item",
            description = "remove item from user cart"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item removed successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                                        {
                                                            "timestamp": "2025-12-23T14:00:00.000Z",
                                                            "status": 401,
                                                            "error": "Unauthorized",
                                                            "message": "Invalid or missing token",
                                                            "path": "/api/v1/cart/item"
                                                        }
                                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "cart item not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/item/{itemId}")
    ResponseEntity<CartResponse> removeItemFromCart(@AuthenticationPrincipal Jwt jwt,@Parameter(description = "ID of the item to remove") @PathVariable Long itemId);
}
