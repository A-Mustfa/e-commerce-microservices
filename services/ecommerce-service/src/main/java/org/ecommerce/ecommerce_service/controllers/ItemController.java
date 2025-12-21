package org.ecommerce.ecommerce_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.dto.ItemRequest;
import org.ecommerce.ecommerce_service.dto.ItemResponse;
import org.ecommerce.ecommerce_service.dto.item.UpdateItemRequest;
import org.ecommerce.ecommerce_service.mappers.ItemMapper;
import org.ecommerce.ecommerce_service.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemResponse> addNewItem(@Valid @RequestBody ItemRequest itemRequest){

        ItemResponse itemResponse = itemMapper.toItemResponse(itemService.createItem(itemRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @DeleteMapping("/remove/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeItem(@PathVariable  Long itemId){
        itemService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/quantity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemResponse> updateQuantity(@Valid @RequestBody UpdateItemRequest updateItemRequest){
        ItemResponse response = itemMapper.toItemResponse(itemService.updateQuantity(updateItemRequest));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemResponse>> findAllItem(){
        List<ItemResponse> response = itemService.getAllItems().stream()
                .map(itemMapper::toItemResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> findItem(@PathVariable  Long itemId){
        ItemResponse response = itemMapper.toItemResponse(itemService.getItem(itemId));
        return ResponseEntity.ok().body(response);
    }
}
