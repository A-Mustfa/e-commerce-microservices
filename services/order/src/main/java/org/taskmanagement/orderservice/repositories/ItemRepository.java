package org.taskmanagement.orderservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.taskmanagement.orderservice.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {
}
