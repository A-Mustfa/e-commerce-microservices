package org.ecommerce.customer.dtos;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
