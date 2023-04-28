package com.xzx.shippingapplication.controller.order;


import com.xzx.shippingapplication.annota.BlockHandler;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.controller.degradation.CommonReduce;
import com.xzx.shippingapplication.controller.degradation.order.ShippingOrderReduce;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.service.ShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzx
 * @since 2023-04-26
 */
@RestController
@RequestMapping("/order")
public class ShippingOrderController {

    @Autowired
    private ShippingOrderService service;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @BlockHandler(value = 30,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/create")
    public R createShippingOrder(@RequestBody ShippingOrder shippingOrder){
        return service.createShippingOrder(shippingOrder);
    }

    @GetMapping("/get-order-by-order-id")
    public R getOrderByOrderId(@RequestParam String orderId){
        return R.ok().data("order",service.getOrderByOrderId(orderId));
    }

    @BlockHandler(value = 1,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/test")
    public R test(String s){
        return R.ok().message(s);
    }



}

