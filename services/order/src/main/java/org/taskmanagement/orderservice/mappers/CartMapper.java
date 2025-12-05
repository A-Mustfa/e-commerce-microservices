package org.taskmanagement.orderservice.mappers;

import org.springframework.stereotype.Component;
import org.taskmanagement.orderservice.dto.CartItemResponse;
import org.taskmanagement.orderservice.dto.CartResponse;
import org.taskmanagement.orderservice.models.Cart;
import org.taskmanagement.orderservice.models.CartItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCustomerId())
                .createdAt(cart.getCreatedAt())
                .items(toCartItemResponseList(cart.getCartItems()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .build();
    }

    private List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems) {
        if (cartItems == null) {
            return List.of();
        }

        return cartItems.stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList());
    }

    private CartItemResponse toCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .itemId(cartItem.getItem().getId())
                .itemName(cartItem.getItem().getName())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}