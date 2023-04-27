package com.xzx.shippingapplication;

import com.xzx.shippingapplication.common.util.rabbit.ProducerMessage;
import com.xzx.shippingapplication.config.RabbitConfig;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ShippingApplicationTests {

    @Autowired
    ProducerMessage producerMessage;

    @Test
    void contextLoads() throws Exception {
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setOrderId(1L);
        shippingOrder.setEstimateCapacity(20.3);
        shippingOrder.setCargoInfo("cargoinfo");
        shippingOrder.setCargoName("name");
        shippingOrder.setConsumerId(1);
        shippingOrder.setCargoWeight(11.2);
        shippingOrder.setEstimateDistance(11.2);
        shippingOrder.setId(1);
        shippingOrder.setLatestDeliveryTime(new Date());
        shippingOrder.setCreateTimestamp(new Date());
        shippingOrder.setUpdateTimestamp(new Date());
        producerMessage.sendMsg(shippingOrder, RabbitConfig.EXCHANGE_FOR_SHIPPING_ORDER,RabbitConfig.ROUTINGKEY_FOR_SHIPPING_ORDER);
    }

}
