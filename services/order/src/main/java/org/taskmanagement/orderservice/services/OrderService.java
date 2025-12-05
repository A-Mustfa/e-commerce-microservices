package org.taskmanagement.orderservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskmanagement.orderservice.dto.PlaceOrderRequest;
import org.taskmanagement.orderservice.exceptions.InsufficientStockException;
import org.taskmanagement.orderservice.exceptions.OrderCannotBeCanceledException;
import org.taskmanagement.orderservice.models.Cart;
import org.taskmanagement.orderservice.models.Item;
import org.taskmanagement.orderservice.models.Order;
import org.taskmanagement.orderservice.models.OrderItem;
import org.taskmanagement.orderservice.repositories.CartRepository;
import org.taskmanagement.orderservice.repositories.ItemRepository;
import org.taskmanagement.orderservice.repositories.OrderRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Order placeOrder(PlaceOrderRequest orderRequest) {
        validatePlaceOrderRequest(orderRequest);
        Cart orderCart = cartService.getCartById(orderRequest.getCartId());
        if (orderCart.getCartItems() == null || orderCart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with empty cart");
        }
        checkForPendingOrders(orderCart.getCustomerId());
        Order placedOrder = Order.createOrderFromCart(orderCart, orderRequest);
        List<OrderItem> orderItems = OrderItem.createOrderItems(placedOrder, orderCart);
        placedOrder.setOrderItems(orderItems);
        updateStock(placedOrder);
        Order savedOrder = orderRepository.save(placedOrder);
        cartRepository.delete(orderCart);
        // TODO: Send order confirmation notification
        return savedOrder;
    }

    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.cancel();
        restoreStock(order);
        Order cancelledOrder = orderRepository.save(order);
        // TODO: Send cancellation notification
        return cancelledOrder;
    }

    private void validatePlaceOrderRequest(PlaceOrderRequest request) {
        if (request.getCartId() == null) {
            throw new IllegalArgumentException("Cart ID is required");
        }
        if (request.getDeliveryAddress() == null || request.getDeliveryAddress().isBlank()) {
            throw new IllegalArgumentException("Delivery address is required");
        }
        if (request.getCustomerPhone() == null || request.getCustomerPhone().isBlank()) {
            throw new IllegalArgumentException("Customer phone is required");
        }
    }

    private void updateStock(Order order) {
        List<Item> updatedItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Item item = cartService.getItem(orderItem.getItemId());
                    decreaseItemStock(item, orderItem.getQuantity());
                    return item;
                }).toList();
        itemRepository.saveAll(updatedItems);
    }

    private void decreaseItemStock(Item item, Integer quantityChange) {
        double newStock = item.getStock() - quantityChange;
        if (newStock < 0) {
            throw new InsufficientStockException(
                    "Insufficient stock for " + item.getName() +
                            ". Available: " + item.getStock() +
                            ", Requested: " + quantityChange
            );
        }
        item.setStock(newStock);
    }

    private void restoreStock(Order order) {
        List<Item> updatedItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Item item = cartService.getItem(orderItem.getItemId());
                    item.setStock(item.getStock() + orderItem.getQuantity());
                    return item;
                }).toList();
        itemRepository.saveAll(updatedItems);
    }

    @Transactional(readOnly = true)
    public List<Order> getCustomerOrders(Long customerId) {
        return orderRepository.findAllByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
    }

    private void checkForPendingOrders(Long customerId) {
        Optional<Order> existingPendingOrder = orderRepository
                .findByCustomerIdAndOrderStatus(customerId, Order.OrderStatus.PENDING);
        if (existingPendingOrder.isPresent()) {
            throw new IllegalStateException(
                    "Customer has a pending order. Please complete or cancel it before placing a new order."
            );
        }
    }
}