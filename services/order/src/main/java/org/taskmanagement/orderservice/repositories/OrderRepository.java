package org.taskmanagement.orderservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.taskmanagement.orderservice.models.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByCustomerId(Long customerId);

    // Better: Find by status
    Optional<Order> findByCustomerIdAndOrderStatus(Long customerId, Order.OrderStatus status);

    // Get all orders for a customer
    List<Order> findAllByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
