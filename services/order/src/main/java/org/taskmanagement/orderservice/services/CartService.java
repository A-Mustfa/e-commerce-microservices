package org.taskmanagement.orderservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskmanagement.orderservice.dto.CartItemRequest;
import org.taskmanagement.orderservice.dto.CustomerResponse;
import org.taskmanagement.orderservice.exceptions.CartItemNotFoundException;
import org.taskmanagement.orderservice.exceptions.CartNotFoundException;
import org.taskmanagement.orderservice.exceptions.ItemAlreadyExists;
import org.taskmanagement.orderservice.models.Cart;
import org.taskmanagement.orderservice.models.CartItem;
import org.taskmanagement.orderservice.models.Item;
import org.taskmanagement.orderservice.proxies.CustomerProxy;
import org.taskmanagement.orderservice.repositories.CartItemRepository;
import org.taskmanagement.orderservice.repositories.CartRepository;
import org.taskmanagement.orderservice.repositories.ItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerProxy customerProxy;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCartById(Long cart_id) {
        Cart cart = cartRepository.findById(cart_id).orElseThrow(
                () ->
                {
                    throw new CartNotFoundException("no such cart with id: " + cart_id);
                });
        return cart;
    }

    @Transactional
    public Cart addCartItem(CartItemRequest cartItemRequest) {
        Cart cart = getCartByCustomerId(cartItemRequest);
        Item requestItem = getItem(cartItemRequest.getItemId());
        if (isCartItemExist(cartItemRequest, cart)) {
            throw new ItemAlreadyExists("cart item already exists");
        }
        CartItem newCartItem = CartItem.buildCartItem(cart, requestItem, cartItemRequest.getQuantity());
        cart.getCartItems().add(newCartItem);
        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long cartId){
        Cart cart = getCartById(cartId);
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public Cart removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = getCartItemById(cartItemId);

        if (!cartItem.getCart().getCartId().equals(cartId)) {
            throw new EntityNotFoundException("Cart item does not belong to this cart");
        }

        cart.removeCartItem(cartItem);
        cartItem.setCart(null);
        return cartRepository.save(cart);
    }

    private CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(
                        () -> new CartItemNotFoundException("no cart item with id: " + cartItemId)
                );
    }

    private boolean isCartItemExist(CartItemRequest cartItemRequest, Cart cart) {
        Optional<CartItem> cartItem = cartItemRepository.findByCart_CartIdAndItem_Id(cart.getCartId(), cartItemRequest.getItemId());
        return cartItem.isPresent();
    }

    public Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException("item not found with id: " + itemId)
        );
        return item;
    }

    private Cart getCartByCustomerId(CartItemRequest itemRequest) {
        Cart cart = cartRepository.getCartByCustomerId(itemRequest.getCustomerId()).orElseThrow(
                () -> new  CartNotFoundException("no such cart with this customer: " + itemRequest.getCustomerId())
        );
        return cart;
    }

    private CustomerResponse getCustomer(CartItemRequest cartItemRequest) {
        CustomerResponse customer = customerProxy.getCustomer(cartItemRequest.getCustomerId());
        return customer;
    }
}
