package com.xzx.shippingapplication;

import cn.itcast.feign.clients.ShippingOrderClient;
import cn.itcast.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xzx.shippingapplication.mapper")
@EnableScheduling
@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class,clients = {ShippingOrderClient.class})
public class CarrierApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrierApplication.class, args);
    }

}
