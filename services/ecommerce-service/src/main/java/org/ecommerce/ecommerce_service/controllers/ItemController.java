package org.ecommerce.ecommerce_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.ecommerce.ecommerce_service.dto.ErrorResponse;
import org.ecommerce.ecommerce_service.dto.item.ItemRequest;
import org.ecommerce.ecommerce_service.dto.item.ItemResponse;
import org.ecommerce.ecommerce_service.dto.item.UpdateItemRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ItemController {
    @Operation(
            summary = "add new item",
            description = "create new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    implementation = ItemRequest.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "item info",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ItemResponse.class),
                                    examples = @ExampleObject(
                                            name = "success response",
                                            value = """
                                                    {
                                                        "id": 5,
                                                        "name": "play station",
                                                        "price": 250,
                                                        "stock": 9  
                                                    }
                                                    """
                                    )

                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "item not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2025-12-23T14:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "item not found",
                                                        "path": "/api/v1/item/add"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ItemResponse> addNewItem(@Valid @RequestBody ItemRequest itemRequest);


    @Operation(
            summary = "Remove item",
            description = "Delete an item from inventory by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item removed successfully",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, examples = @ExampleObject(value = "Item removed successfully"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @DeleteMapping("/remove/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> removeItem(@Parameter(description = "ID of the item to delete") @PathVariable Long itemId);

    @Operation(
            summary = "Update item quantity",
            description = "Update the stock/quantity of an existing item",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateItemRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Quantity updated successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ItemResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PatchMapping("/quantity")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ItemResponse> updateQuantity(@Valid @RequestBody UpdateItemRequest updateItemRequest);

    @Operation(
            summary = "Get all items",
            description = "Retrieve a list of all items in the inventory",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemResponse.class))
                    )
            }
    )
    @GetMapping("/all")
    ResponseEntity<List<ItemResponse>> findAllItem(@RequestParam(required = false, defaultValue = "0") int page , @RequestParam(required = false, defaultValue = "10") int pageSize,String orderBy, String orderDirection,String search);

    @Operation(
            summary = "Get item by ID",
            description = "Retrieve details of a single item",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{itemId}")
    ResponseEntity<ItemResponse> findItem(@Parameter(description = "ID of the item to retrieve") @PathVariable Long itemId);
}
