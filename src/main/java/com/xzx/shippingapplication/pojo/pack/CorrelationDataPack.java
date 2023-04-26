package com.xzx.shippingapplication.pojo.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * <Description> CorrelationDataPack
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.pojo.pack
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CorrelationDataPack {

    private CorrelationData correlationData;

    private String exchangeName;

    private String routingKey;

    private String data;

}
