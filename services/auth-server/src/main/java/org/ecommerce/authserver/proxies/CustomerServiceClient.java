package org.ecommerce.authserver.proxies;

import org.ecommerce.authserver.configuration.FeignJwtConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ECOMMERCE-SERVICE",
    configuration = FeignJwtConfig.class)
public interface CustomerServiceClient {

    @PostMapping("/api/v1/customer/create")
    void createCustomer(@RequestBody Long userId);
}