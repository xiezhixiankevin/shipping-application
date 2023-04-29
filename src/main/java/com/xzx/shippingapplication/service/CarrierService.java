package com.xzx.shippingapplication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.shippingapplication.pojo.Carrier;
import com.xzx.shippingapplication.pojo.CarrierSamllTruck;
import com.xzx.shippingapplication.pojo.ShippingOrder;

public interface CarrierService extends IService<Carrier>{

    /**
     * 根据订单 分配车
     * @param order
     */
    void allocation(ShippingOrder order);
}
