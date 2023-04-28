package com.xzx.shippingapplication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.shippingapplication.pojo.ShippingOrder;

public interface CarrierService {

    /**
     * 根据订单 分配车
     * @param order
     */
    void allocation(ShippingOrder order);
}
