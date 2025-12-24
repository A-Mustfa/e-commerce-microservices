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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

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

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toItemResponse)
                .toList();
    }
}
