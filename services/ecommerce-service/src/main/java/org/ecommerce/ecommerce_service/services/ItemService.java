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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Cacheable(value = "item", key = "#page + '-' + #pageSize + '-' + #orderBy + '-' + #orderDirection + '-' + (#search == null ? '' : #search)")
    public List<ItemResponse> getAllItems(int page, int pageSize,String orderBy, String orderDirection,String search) {
        Sort sort = null;
        if(orderDirection.equals("asc")) {
            sort = Sort.by(orderBy).ascending();
        }else if(orderDirection.equals("desc")) {
            sort = Sort.by(orderBy).descending();
        }
        Pageable pageRequest = PageRequest.of(page, pageSize,sort);
        if(search == null) {
            List<ItemResponse> items = itemRepository.findAll(pageRequest).map(itemMapper::toItemResponse).getContent();
            return items;
        }else{
            return getItemsBySearch(search,pageRequest);
        }
    }

    private Sort getSortType(String orderBy, String orderDirection) {
        Sort sort = null;
        if(orderDirection.equals("asc")) {
            sort = Sort.by(orderBy).ascending();
        }else if(orderDirection.equals("desc")) {
            sort = Sort.by(orderBy).descending();
        }
        return sort;
    }

    public List<ItemResponse> getItemsBySearch(String search, Pageable  pageable) {
        List<ItemResponse> items = itemRepository.findByNameLike("%" + search + "%", pageable)
                .map(itemMapper::toItemResponse)
                .getContent();
        return items;
    }
}
