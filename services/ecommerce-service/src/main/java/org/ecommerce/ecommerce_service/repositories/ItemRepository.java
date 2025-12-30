package org.ecommerce.ecommerce_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ecommerce.ecommerce_service.models.Item;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {

    Optional<Item> findByName(String name);
    Page<Item> findAll(Pageable pageable);

    Page<Item> findByNameLike(String name,Pageable pageable);
}
