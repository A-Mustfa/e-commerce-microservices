package org.ecommerce.ecommerce_service.controllers.impl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.ecommerce.ecommerce_service.dto.cart.CartResponse;
import org.ecommerce.ecommerce_service.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "BearerJWT")
@Tag(name = "Cart", description = "Operations related to user shopping cart")
public class CartControllerImpl implements org.ecommerce.ecommerce_service.controllers.CartController {

    private final CartService cartService;

    @Override
    @GetMapping("/my-cart")
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.getCartByUserId(jwt.getClaim("userId"));
        return ResponseEntity.ok().body(cartResponse);
    }

    @Override
    @DeleteMapping("/my-cart")
    public ResponseEntity<CartResponse> clearCart(@AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.clearCart(jwt.getClaim("userId"));
        return ResponseEntity.ok().body(cartResponse);
    }

    @Override
    @PostMapping("/item")
    public ResponseEntity<CartResponse> addItemToCart(@Valid @RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartService.addCartItem(cartItemRequest,jwt.getClaim("userId"));
        return ResponseEntity.ok().body(cartResponse);
    }

    @Override
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemId) {
        CartResponse cartResponse = cartService.removeItemFromCart(jwt.getClaim("userId"),itemId);
        return ResponseEntity.ok().body(cartResponse);
    }
}
