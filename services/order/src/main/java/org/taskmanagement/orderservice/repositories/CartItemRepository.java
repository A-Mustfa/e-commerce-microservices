package org.taskmanagement.orderservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.taskmanagement.orderservice.models.CartItem;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem,Long> {
    Optional<CartItem> findByCart_CartIdAndItem_Id(Long cartId, Long itemId);
}
