package com.xzx.shippingapplication.service.impl;

import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.TimeUtils;
import com.xzx.shippingapplication.common.util.rabbit.ProducerMessage;
import com.xzx.shippingapplication.config.RabbitConfig;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.mapper.ShippingOrderMapper;
import com.xzx.shippingapplication.service.ShippingOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-26
 */
@Service
public class ShippingOrderServiceImpl extends ServiceImpl<ShippingOrderMapper, ShippingOrder> implements ShippingOrderService {

    private final Random random = new Random();

    @Autowired
    ProducerMessage producerMessage;

    // 生成唯一订单id
    private String generateOrderId() {
        String replaceUUID = UUID.randomUUID().toString().replace("-", "");
        return replaceUUID;
    }

    private double estimateCapacity(double weight,int lastHours,double km){
        // weight x km / lastHours
        return weight * km / (lastHours <= 0? 1: lastHours);
    }

    @Override
    public R createShippingOrder(ShippingOrder shippingOrder) {
        // 生成订单号
        shippingOrder.setOrderId(generateOrderId());
        // 计算预估运力(重量，时间，距离)
        Date nowTime = new Date();
        Date latestDeliveryTime = shippingOrder.getLatestDeliveryTime();
        double capacity = estimateCapacity(shippingOrder.getCargoWeight(), TimeUtils.getDifferHour(nowTime, latestDeliveryTime), shippingOrder.getEstimateDistance());
        shippingOrder.setEstimateCapacity(capacity);
        // 将订单信息写入数据库
        if(save(shippingOrder)){
            /**
             * 生成消息放到rabbitMQ队列
             * */
            producerMessage.sendMsg(shippingOrder, RabbitConfig.EXCHANGE_FOR_SHIPPING_ORDER,RabbitConfig.ROUTINGKEY_FOR_SHIPPING_ORDER);
            return R.ok().data("order",shippingOrder);
        }

        return null;
    }

}
