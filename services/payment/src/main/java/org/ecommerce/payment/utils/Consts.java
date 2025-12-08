package org.ecommerce.payment.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Consts {
    public static final BigDecimal STATIC_AMOUNT = BigDecimal.valueOf(500.0);
}
