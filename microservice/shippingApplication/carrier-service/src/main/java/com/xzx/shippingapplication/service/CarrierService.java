package com.xzx.shippingapplication.service;

import cn.itcast.feign.common.R;
import cn.itcast.feign.pojo.Carrier;
import cn.itcast.feign.pojo.ShippingOrder;
import com.baomidou.mybatisplus.extension.service.IService;


public interface CarrierService extends IService<Carrier>{

    /**
     * 根据订单 分配车
     * @param order
     */
    void allocation(ShippingOrder order);

    /**
     * 查看运力池详细信息
     * @return
     */
    R getAllTransportationInfo();


}
