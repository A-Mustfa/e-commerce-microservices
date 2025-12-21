package org.ecommerce.ecommerce_service.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ecommerce.ecommerce_service.models.CartItem;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem,Long> {

    Optional<CartItem> findByCart_CartIdAndItem_Id(Long cartId, Long itemId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart.cartId = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);
}
