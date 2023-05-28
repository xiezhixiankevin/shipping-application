package com.ship.order.service.impl;

import cn.itcast.feign.common.R;
import cn.itcast.feign.util.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ship.order.config.RabbitConfig;
import com.ship.order.pojo.LogisticsRecord;
import com.ship.order.pojo.ShippingOrder;
import com.ship.order.mapper.ShippingOrderMapper;
import com.ship.order.service.LogisticsRecordService;
import com.ship.order.service.ShippingOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ship.order.rabbit.ProducerMessage;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-26
 */
@Slf4j
@Service
public class ShippingOrderServiceImpl extends ServiceImpl<ShippingOrderMapper, ShippingOrder> implements ShippingOrderService {

    public static final Integer STATE_PUBLISH = 0;
    public static final Integer STATE_NEGOTIATE = 1;
    public static final Integer STATE_WAITING = 2;
    public static final Integer STATE_TRANSPORT = 3;
    public static final Integer STATE_ARRIVED = 4;
    public static final Integer STATE_COMPLETED = 5;

    @Autowired
    private ProducerMessage producerMessage;

    @Autowired
    private LogisticsRecordService logisticsRecordService;

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

    @Override
    public Boolean updateOrderStateByInTransitId(Integer inTransitId, Integer state) {
        QueryWrapper<ShippingOrder> shippingOrderQueryWrapper = new QueryWrapper<>();
        shippingOrderQueryWrapper.eq("in_transit_id",inTransitId);

        try{
            // 更新订单表
            ShippingOrder shippingOrder = new ShippingOrder();
            shippingOrder.setState(state);
            update(shippingOrder,shippingOrderQueryWrapper);

            // 如果是state是 3(发车) 4(送达)，在物流记录表新增记录
            List<ShippingOrder> shippingOrderList = list(shippingOrderQueryWrapper);// 获取所有相关订单
            List<LogisticsRecord> logisticsRecordList = new ArrayList<>();

            for (ShippingOrder each:shippingOrderList){
                LogisticsRecord logisticsRecord = new LogisticsRecord();
                logisticsRecord.setOrderId(each.getId());
                logisticsRecord.setState(state);
                if(state.equals(STATE_TRANSPORT)){
                    logisticsRecord.setContent("快件已发车");
                }else if(state.equals(STATE_COMPLETED)) {
                    logisticsRecord.setContent("快件已送达");
                }
            }
            // 更新物流记录表
            logisticsRecordService.saveBatch(logisticsRecordList);

            return true;
        }catch (Exception e){
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public Boolean updateOrderStateById(Integer id, Integer state) {
        QueryWrapper<ShippingOrder> shippingOrderQueryWrapper = new QueryWrapper<>();
        shippingOrderQueryWrapper.eq("id",id);

        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setState(state);

        return update(shippingOrder,shippingOrderQueryWrapper);
    }

    @Override
    public Boolean addLogisticsRecord(LogisticsRecord logisticsRecord) {
        return logisticsRecordService.save(logisticsRecord);
    }

    @Override
    public List<LogisticsRecord> listLogisticsRecord(Integer orderId) {
        QueryWrapper<LogisticsRecord> logisticsRecordQueryWrapper = new QueryWrapper<>();
        logisticsRecordQueryWrapper.eq("order_id",orderId).orderByAsc("create_timestamp");

        return logisticsRecordService.list(logisticsRecordQueryWrapper);
    }

}
