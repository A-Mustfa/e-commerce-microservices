package org.ecommerce.ecommerce_service.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class ItemResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private double stock;


}
