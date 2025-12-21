package org.ecommerce.ecommerce_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.ItemRequest;
import org.ecommerce.ecommerce_service.dto.item.UpdateItemRequest;
import org.ecommerce.ecommerce_service.exceptions.ItemAlreadyExists;
import org.ecommerce.ecommerce_service.mappers.ItemMapper;
import org.ecommerce.ecommerce_service.models.Item;
import org.ecommerce.ecommerce_service.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Item createItem(ItemRequest itemRequest) {
        if(isItemExist(itemRequest.getName())){
            throw new ItemAlreadyExists("Item already exists");
        }
        Item newItem = itemMapper.toItem(itemRequest);
        return itemRepository.save(newItem);
    }

    private boolean isItemExist(String name){
        Optional<Item> item = itemRepository.findByName(name);
        return item.isPresent();
    }

    public void removeItem(Long itemId) {
        Item item = getItem(itemId);
        itemRepository.delete(item);
    }

    public Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException("no such item with id" + itemId)
        );
        return item;
    }

    public Item updateQuantity(UpdateItemRequest updateItemRequest) {
        Item item = getItem(updateItemRequest.itemId());
        item.setStock(updateItemRequest.quantity());
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
