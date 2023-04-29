package com.xzx.shippingapplication.controller.order;


import com.xzx.shippingapplication.annota.BlockHandler;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.UserAccountPackHolder;
import com.xzx.shippingapplication.controller.degradation.CommonReduce;
import com.xzx.shippingapplication.controller.degradation.order.ShippingOrderReduce;
import com.xzx.shippingapplication.pojo.LogisticsRecord;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import com.xzx.shippingapplication.service.LogisticsRecordService;
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
    private ShippingOrderService shippingOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 创建订单
     * */
    @BlockHandler(value = 30,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/create")
    public R createShippingOrder(@RequestBody ShippingOrder shippingOrder){
        UserAccountPack user = UserAccountPackHolder.getUser();
        shippingOrder.setConsumerId(user.getId());
        return shippingOrderService.createShippingOrder(shippingOrder);
    }

    /**
     * 根据订单id返回订单信息
     * */
    @GetMapping("/get-order-by-order-id")
    public R getOrderByOrderId(@RequestParam String orderId){
        return R.ok().data("order",shippingOrderService.getOrderByOrderId(orderId));
    }

    /**
     * 列出某个客户的订单
     * */
    @GetMapping("/get-consumer-orders")
    public R getConsumerOrders(@RequestParam Integer consumerId,
                                          @RequestParam Boolean ifCompleted){
        return R.ok().data("order_list",shippingOrderService.listOrdersOfConsumer(ifCompleted,consumerId));
    }

    /**
     * 修改某个订单的信息
     * */
    @PutMapping("/update-order-by-id")
    public R updateOrderById(@RequestBody ShippingOrder shippingOrder){
        if(shippingOrderService.updateById(shippingOrder)){
            return R.ok();
        }
        return R.error();
    }

    /**
     * 给订单添加物流信息
     * */
    @PostMapping("/add-logistics-record-by-id")
    public R addLogisticsRecordById(@RequestBody LogisticsRecord logisticsRecord){

       if(shippingOrderService.addLogisticsRecord(logisticsRecord)){
           return R.ok().message("物流信息更新成功...");
       }else {
           return R.error().message("抱歉物流信息更新失败，请重试...");
       }
    }

    /**
     * 获取订单的所有物流信息，默认时间排序
     * */
    @GetMapping("/list-logistics-record")
    public R listLogisticsRecord(@RequestParam Integer orderId){
        return R.ok().data("logistics_record_list",shippingOrderService.listLogisticsRecord(orderId));
    }

    @BlockHandler(value = 1,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制1个请求
    @PostMapping("/test")
    public R test(String s){
        return R.ok().message(s);
    }



}

