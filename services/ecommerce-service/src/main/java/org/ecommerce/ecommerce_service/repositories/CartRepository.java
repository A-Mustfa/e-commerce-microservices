package org.ecommerce.ecommerce_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ecommerce.ecommerce_service.models.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
   Optional<Cart> getCartByUserId(Long customerId);
}

