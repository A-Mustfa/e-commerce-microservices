package org.ecommerce.ecommerce_service.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int code,
        String error,
        Map<String, String> errors
) {
}
