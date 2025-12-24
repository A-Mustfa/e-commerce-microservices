package org.ecommerce.ecommerce_service.mappers;
import org.ecommerce.ecommerce_service.dto.item.ItemRequest;
import org.ecommerce.ecommerce_service.dto.item.ItemResponse;
import org.ecommerce.ecommerce_service.models.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ItemMapper {

    Item toItem(ItemRequest itemRequest);
    ItemResponse toItemResponse(Item item);
}
