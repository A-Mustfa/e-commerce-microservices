package org.ecommerce.ecommerce_service.services;

import commons.utils.Exceptions.ItemAlreadyExists;
import commons.utils.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.item.ItemRequest;
import org.ecommerce.ecommerce_service.dto.item.ItemResponse;
import org.ecommerce.ecommerce_service.dto.item.UpdateItemRequest;
import org.ecommerce.ecommerce_service.mappers.ItemMapper;
import org.ecommerce.ecommerce_service.models.Item;
import org.ecommerce.ecommerce_service.repositories.ItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;


    @Caching(
            put = { @CachePut(value = "item", key = "#result.id") },
            evict = { @CacheEvict(value = "item", key = "'all'") }
    )
    public ItemResponse createItem(ItemRequest itemRequest) {
        if(isItemExist(itemRequest.getName())){
            throw new ItemAlreadyExists("Item already exists");
        }
        Item newItem = itemMapper.toItem(itemRequest);
        itemRepository.save(newItem);
        return  itemMapper.toItemResponse(newItem);
    }

    private boolean isItemExist(String name){
        Optional<Item> item = itemRepository.findByName(name);
        return item.isPresent();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "item", key = "#itemId"),
            @CacheEvict(value = "item", key = "'all'")
    })
    public void removeItem(Long itemId) {
        Item item = getItem(itemId);
        itemRepository.delete(item);
    }


    public Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("no such item with id" + itemId)
        );
        return item;
    }

    @Cacheable(value = "item", key = "#itemId")
    public ItemResponse getItemById(Long itemId) {
        Item item = getItem(itemId);
        return itemMapper.toItemResponse(item);
    }


    public ItemResponse updateQuantity(UpdateItemRequest updateItemRequest) {
        Item item = getItem(updateItemRequest.itemId());
        item.setStock(updateItemRequest.quantity());
        itemRepository.save(item);
        return itemMapper.toItemResponse(item);
    }

    @Cacheable(value = "item", key = "'all'")
    public List<ItemResponse> getAllItems() {
        List<ItemResponse> items = new ArrayList<>();
        itemRepository.findAll().forEach(item ->
                items.add(itemMapper.toItemResponse(item))
        );
        return items;
    }
}
