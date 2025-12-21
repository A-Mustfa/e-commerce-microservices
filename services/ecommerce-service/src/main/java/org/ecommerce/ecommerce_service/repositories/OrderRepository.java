package org.ecommerce.ecommerce_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ecommerce.ecommerce_service.models.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    // Better: Find by status
    Optional<Order> findByUserIdAndOrderStatus(Long userId, Order.OrderStatus status);

    // Get all orders for a customer
    List<Order> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
