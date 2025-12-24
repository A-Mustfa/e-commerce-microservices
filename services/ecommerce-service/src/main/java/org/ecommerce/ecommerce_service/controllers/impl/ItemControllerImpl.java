package org.ecommerce.ecommerce_service.controllers.impl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerce_service.controllers.ItemController;
import org.ecommerce.ecommerce_service.dto.item.ItemRequest;
import org.ecommerce.ecommerce_service.dto.item.ItemResponse;
import org.ecommerce.ecommerce_service.dto.item.UpdateItemRequest;
import org.ecommerce.ecommerce_service.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerJWT")
@Tag(name = "Item", description = "Operations related to Item info")
public class ItemControllerImpl implements ItemController {

    private final ItemService itemService;
    
    @Override
    public ResponseEntity<ItemResponse> addNewItem(@Valid @RequestBody ItemRequest itemRequest){
        ItemResponse itemResponse = itemService.createItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @Override
    public ResponseEntity<String> removeItem(@PathVariable Long itemId){
        itemService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ItemResponse> updateQuantity(@Valid @RequestBody UpdateItemRequest updateItemRequest){
        ItemResponse response = itemService.updateQuantity(updateItemRequest);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<ItemResponse>> findAllItem(){
        List<ItemResponse> response = itemService.getAllItems();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ItemResponse> findItem(@PathVariable Long itemId){
        ItemResponse response = itemService.getItemById(itemId);
        return ResponseEntity.ok().body(response);
    }
}
