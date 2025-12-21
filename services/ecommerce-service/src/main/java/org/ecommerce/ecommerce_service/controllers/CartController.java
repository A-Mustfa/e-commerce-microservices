package org.ecommerce.ecommerce_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.models.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.ecommerce.ecommerce_service.dto.cart.CartResponse;
import org.ecommerce.ecommerce_service.mappers.CartMapper;
import org.ecommerce.ecommerce_service.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/my-cart")
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.getCart(jwt.getClaim("userId")));
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/my-cart")
    public ResponseEntity<CartResponse> clearCart(@AuthenticationPrincipal Jwt jwt) {
        Cart clearedCart = cartService.clearCart(jwt.getClaim("userId"));
        CartResponse cartResponse = cartMapper.toCartResponse(clearedCart);
        return ResponseEntity.ok().body(cartResponse);
    }

    @PostMapping("/item")
    public ResponseEntity<CartResponse> addItemToCart(@Valid @RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal Jwt jwt) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.addCartItem(cartItemRequest,jwt.getClaim("userId")));
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@AuthenticationPrincipal Jwt jwt,@PathVariable Long  itemId) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.removeItemFromCart(jwt.getClaim("userId"),itemId));
        return ResponseEntity.ok().body(cartResponse);
    }
}
