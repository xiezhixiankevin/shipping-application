package com.xzx.shippingapplication.service.impl;

import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.service.CarrierService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-27
 */
@Service
public class CarrierServiceImpl implements CarrierService {


    @Override
    public void allocation(ShippingOrder order) {
        System.out.println("111111111111111"+order.getProviderId());

        //根据承运商+出发地+目的地+是否加急+是否冷藏+当前剩余容量=查看车辆信息

        //将这个订单分配给这辆车

        //装车
        //如果这个订单是这辆车的第一单，就生成在途记录，

        //不是第一单，更新数据



    }
}
