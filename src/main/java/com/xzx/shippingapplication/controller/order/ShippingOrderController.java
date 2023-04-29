package com.xzx.shippingapplication.controller.order;


import com.xzx.shippingapplication.annota.BlockHandler;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.UserAccountPackHolder;
import com.xzx.shippingapplication.controller.degradation.CommonReduce;
import com.xzx.shippingapplication.controller.degradation.order.ShippingOrderReduce;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
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

    /**
     * 创建订单
     * */
    @BlockHandler(value = 30,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/create")
    public R createShippingOrder(@RequestBody ShippingOrder shippingOrder){
        UserAccountPack user = UserAccountPackHolder.getUser();
        System.out.println("非常重要！！！！！！！！！:"+user);
//        shippingOrder.setConsumerId()
        return service.createShippingOrder(shippingOrder);
    }

    /**
     * 根据订单id返回订单信息
     * */
    @GetMapping("/get-order-by-order-id")
    public R getOrderByOrderId(@RequestParam String orderId){
        return R.ok().data("order",service.getOrderByOrderId(orderId));
    }

    /**
     * 列出某个客户的订单
     * */
    @GetMapping("/get-consumer-uncompleted-orders")
    public R getConsumerUncompletedOrders(@RequestParam Integer consumerId,
                                          @RequestParam Boolean ifCompleted){
        return R.ok().data("order_list",service.listOrdersOfConsumer(ifCompleted,consumerId));
    }

    /**
     * 修改某个订单的信息
     * */
    @PutMapping("/update-order-by-id")
    public R updateOrderById(@RequestBody ShippingOrder shippingOrder){
        return R.ok().data("order",service.updateById(shippingOrder));
    }

    @BlockHandler(value = 1,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/test")
    public R test(String s){
        return R.ok().message(s);
    }



}

