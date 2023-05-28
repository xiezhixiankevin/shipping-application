package cn.itcast.feign.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "userservice")
public interface ShippingOrderClient {

    @PostMapping("/user/{id}")
    void findById(@PathVariable("id") Long id);
}
