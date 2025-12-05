package org.taskmanagement.orderservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.taskmanagement.orderservice.models.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
   Optional<Cart> getCartByCustomerId(Long customerId);
}

