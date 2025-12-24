package org.ecommerce.ecommerce_service.services;

import commons.utils.Exceptions.InsufficientStockException;
import commons.utils.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.OrderConfirmation;
import org.ecommerce.ecommerce_service.dto.PaymentResponse;
import org.ecommerce.ecommerce_service.dto.order.OrderResponse;
import org.ecommerce.ecommerce_service.kafka.OrderProducer;
import org.ecommerce.ecommerce_service.mappers.OrderMapper;
import org.ecommerce.ecommerce_service.models.*;
import org.ecommerce.ecommerce_service.proxies.PaymentProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ecommerce.ecommerce_service.dto.PaymentRequest;
import org.ecommerce.ecommerce_service.repositories.CartRepository;
import org.ecommerce.ecommerce_service.repositories.ItemRepository;
import org.ecommerce.ecommerce_service.repositories.OrderRepository;
import java.util.List;
import java.util.Optional;
import static org.ecommerce.ecommerce_service.models.Order.OrderStatus.CANCELLED;
import static org.ecommerce.ecommerce_service.models.Order.OrderStatus.CONFIRMED;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ItemRepository itemRepository;
    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final PaymentProxy paymentProxy;
    private final ItemService itemService;
    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse placeOrder(Long userId, String email) {
        Cart cart = cartService.getCart(userId);
        Customer customer = customerService.getCustomer(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with empty cart");
        }
        checkForPendingOrders(userId);
        Order order = Order.createOrderFromCart(cart, customer);
        order.setOrderItems(OrderItem.createOrderItems(order, cart));
        Order savedOrder = orderRepository.save(order);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .amount(savedOrder.getAmount())
                .build();
        PaymentResponse response = paymentProxy.purchase(paymentRequest);
        if(response.status().equals("DENIED")){
            savedOrder.setOrderStatus(CANCELLED);
        } else if (response.status().equals("PAYED")) {
            savedOrder.setOrderStatus(CONFIRMED);
            updateStock(savedOrder);
            cartRepository.delete(cart);
        }
        orderProducer.sendConfirmation(
                new OrderConfirmation(
                        order.getId(),
                        email,
                        order.getAmount(),
                        customer
                )
        );
        orderRepository.save(savedOrder);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.cancel();
        restoreStock(order);
        Order cancelledOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(cancelledOrder);
    }

    @Transactional
    public void updateStock(Order order) {
        List<Item> updatedItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Item item = itemService.getItem(orderItem.getItemId());
                    decreaseItemStock(item, orderItem.getQuantity());
                    return item;
                }).toList();
        itemRepository.saveAll(updatedItems);
    }

    @Transactional
    public void decreaseItemStock(Item item, Integer quantityChange) {
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

    @Transactional
    public void restoreStock(Order order) {
        List<Item> updatedItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Item item = itemService.getItem(orderItem.getItemId());
                    item.setStock(item.getStock() + orderItem.getQuantity());
                    return item;
                }).toList();
        itemRepository.saveAll(updatedItems);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return orderMapper.toOrderResponse(order);
    }

    private void checkForPendingOrders(Long userId) {
        Optional<Order> existingPendingOrder = orderRepository
                .findByUserIdAndOrderStatus(userId, Order.OrderStatus.PENDING);
        if (existingPendingOrder.isPresent()) {
            throw new IllegalStateException(
                    "Customer has a pending order. Please complete or cancel it before placing a new order."
            );
        }
    }

}