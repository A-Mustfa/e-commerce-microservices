package org.taskmanagement.orderservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.taskmanagement.orderservice.dto.CartItemRequest;
import org.taskmanagement.orderservice.dto.CartResponse;
import org.taskmanagement.orderservice.mappers.CartMapper;
import org.taskmanagement.orderservice.models.Cart;
import org.taskmanagement.orderservice.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;
    @GetMapping("/{cartId}")
    @PreAuthorize("hasAuthority('SCOPE_customer')")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long  cartId) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.getCartById(cartId));
        return ResponseEntity.ok(cartResponse);
    }
    @DeleteMapping("/{cartId}")
    public ResponseEntity<CartResponse> clearCart(@PathVariable Long  cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/item")
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody @Valid CartItemRequest cartItemRequest) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.addCartItem(cartItemRequest));
        return ResponseEntity.ok(cartResponse);
    }
    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long  itemId) {
        CartResponse cartResponse = cartMapper.toCartResponse(cartService.removeItemFromCart(cartId,itemId));
        return ResponseEntity.ok(cartResponse);
    }
}
