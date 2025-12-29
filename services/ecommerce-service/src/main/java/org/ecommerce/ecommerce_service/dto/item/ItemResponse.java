package org.ecommerce.ecommerce_service.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private double stock;


}
