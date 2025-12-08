package org.taskmanagement.orderservice.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.taskmanagement.orderservice.dto.CustomerResponse;

@FeignClient(name = "customer-service", url = "http://localhost:8002")
public interface CustomerProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/customer")
    CustomerResponse getCustomer(Long id);
}
