package com.xzx.shippingapplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.*;

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

    public static final Integer STATE_PUBLISH = 0;
    public static final Integer STATE_NEGOTIATE = 1;
    public static final Integer STATE_WAITING = 2;
    public static final Integer STATE_TRANSPORT = 3;
    public static final Integer STATE_ARRIVED = 4;
    public static final Integer STATE_COMPLETED = 5;

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

    @Override
    public ShippingOrder getOrderByOrderId(String orderId) {
        QueryWrapper<ShippingOrder> shippingOrderQueryWrapper = new QueryWrapper<>();
        shippingOrderQueryWrapper.eq("order_id",orderId);
        return getOne(shippingOrderQueryWrapper);
    }

    @Override
    public List<ShippingOrder> listOrdersOfConsumer(Boolean ifCompleted,Integer consumerId) {
        QueryWrapper<ShippingOrder> shippingOrderQueryWrapper = new QueryWrapper<>();
        shippingOrderQueryWrapper.eq("consumer_id",consumerId);
        if(ifCompleted){
            shippingOrderQueryWrapper.eq("state",STATE_COMPLETED);
        }else {
            shippingOrderQueryWrapper.ne("state",STATE_COMPLETED);
        }
        return list(shippingOrderQueryWrapper);
    }

    @Override
    public List<ShippingOrder> listOrdersOfConsumer(Integer consumerId) {
        QueryWrapper<ShippingOrder> shippingOrderQueryWrapper = new QueryWrapper<>();
        shippingOrderQueryWrapper.eq("consumer_id",consumerId);
        return list(shippingOrderQueryWrapper);
    }

}
