package org.ecommerce.ecommerce_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"org.ecommerce.ecommerce_service","commons.utils"})
@EnableFeignClients(basePackages = "org.ecommerce.ecommerce_service.proxies")
@OpenAPIDefinition(
        info = @Info(title = "E-Commerce Server", version = "1.0"),
        tags = {@Tag(name = "Cart"),
                @Tag(name = "Customer"),
                @Tag(name = "Order"),
                @Tag(name = "Item")
        },
        security = @SecurityRequirement(name = "BearerJWT")
)
@SecurityScheme(
        name = "BearerJWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
