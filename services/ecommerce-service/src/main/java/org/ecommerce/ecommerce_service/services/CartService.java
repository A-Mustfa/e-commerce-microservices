package org.ecommerce.ecommerce_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemRequest;
import org.ecommerce.ecommerce_service.exceptions.CartItemNotFoundException;
import org.ecommerce.ecommerce_service.exceptions.CartNotFoundException;
import org.ecommerce.ecommerce_service.exceptions.ItemAlreadyExists;
import org.ecommerce.ecommerce_service.models.Cart;
import org.ecommerce.ecommerce_service.models.CartItem;
import org.ecommerce.ecommerce_service.models.Item;
import org.ecommerce.ecommerce_service.repositories.CartItemRepository;
import org.ecommerce.ecommerce_service.repositories.CartRepository;
import org.ecommerce.ecommerce_service.repositories.ItemRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    @Transactional
    public Cart addCartItem(CartItemRequest cartItemRequest, Long userId) {
        Cart cart = getOrCreateCartForUser(userId);
        Item requestItem = itemService.getItem(cartItemRequest.getItemId());
        if (cartItemService.isCartItemExist(cartItemRequest, cart)) {
            throw new ItemAlreadyExists("cart item already exists");
        }
        CartItem newCartItem = CartItem.buildCartItem(cart, requestItem, cartItemRequest.getQuantity());
        cart.getCartItems().add(newCartItem);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart clearCart(Long authId){
        Cart cart = getCart(authId);
        cartItemRepository.deleteByCartId(cart.getCartId());
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(Long userId, Long cartItemId) {
        Cart cart = getCart(userId);
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (!cartItem.getCart().equals(cart)) {
            throw new CartItemNotFoundException("Cart item does not belong to this cart");
        }

        cart.removeCartItem(cartItem);
        cartItem.setCart(null);
        return cartRepository.save(cart);
    }

    private Cart getOrCreateCartForUser(Long userId) {
        return cartRepository.getCartByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    public Cart getCart(Long userId) {
        Cart cart = cartRepository.getCartByUserId(userId).orElseThrow(
                () -> new  CartNotFoundException("no cart associated with this customer: " + userId)
        );
        return cart;
    }

}
