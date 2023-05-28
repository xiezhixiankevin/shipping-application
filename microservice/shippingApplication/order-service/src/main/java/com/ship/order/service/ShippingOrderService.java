package com.ship.order.service;

import cn.itcast.feign.common.R;
import com.ship.order.pojo.LogisticsRecord;
import com.ship.order.pojo.ShippingOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzx
 * @since 2023-04-26
 */
public interface ShippingOrderService extends IService<ShippingOrder> {
    R createShippingOrder(ShippingOrder shippingOrder);

    ShippingOrder getOrderByOrderId(String orderId);

    List<ShippingOrder> listOrdersOfConsumer(Boolean ifCompleted,Integer consumerId);

    List<ShippingOrder> listOrdersOfConsumer(Integer consumerId);

    Boolean updateOrderStateByInTransitId(Integer inTransitId,Integer state);

    Boolean updateOrderStateById(Integer id,Integer state);

    Boolean addLogisticsRecord(LogisticsRecord logisticsRecord);

    List<LogisticsRecord> listLogisticsRecord(Integer orderId);
}
