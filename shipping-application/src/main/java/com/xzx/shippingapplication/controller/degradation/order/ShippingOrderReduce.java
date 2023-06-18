package com.xzx.shippingapplication.controller.degradation.order;

import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <Description> ShippingOrderReduce
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.controller.degradation.order
 */
@Component
public class ShippingOrderReduce {

    public static final String KEY_BASE_PATH = "BlockHandler.com.xzx.shippingapplication.controller.order.ShippingOrderController.";

    @Autowired
    private RedisTemplate redisTemplate;


}
