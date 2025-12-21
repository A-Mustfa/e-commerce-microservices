package org.ecommerce.ecommerce_service.services;

import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.ecommerce.ecommerce_service.exceptions.CartItemNotFoundException;
import org.ecommerce.ecommerce_service.models.Cart;
import org.ecommerce.ecommerce_service.models.CartItem;
import org.ecommerce.ecommerce_service.repositories.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(
                        () -> new CartItemNotFoundException("no cart item with id: " + cartItemId)
                );
    }

    public boolean isCartItemExist(CartItemRequest cartItemRequest, Cart cart) {
        Optional<CartItem> cartItem = cartItemRepository.findByCart_CartIdAndItem_Id(cart.getCartId(), cartItemRequest.getItemId());
        return cartItem.isPresent();
    }
}
