package org.ecommerce.ecommerce_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ecommerce.ecommerce_service.models.Item;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {

    Optional<Item> findByName(String name);
}
