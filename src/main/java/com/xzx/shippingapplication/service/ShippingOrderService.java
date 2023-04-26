package com.xzx.shippingapplication.service;

import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
