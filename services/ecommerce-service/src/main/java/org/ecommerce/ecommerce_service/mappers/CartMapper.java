package org.ecommerce.ecommerce_service.mappers;

import org.springframework.stereotype.Component;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemResponse;
import org.ecommerce.ecommerce_service.dto.cart.CartResponse;
import org.ecommerce.ecommerce_service.models.Cart;
import org.ecommerce.ecommerce_service.models.CartItem;

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
                .userId(cart.getUserId())
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