package com.xzx.shippingapplication.controller.order;


import com.sun.org.apache.xpath.internal.operations.Mod;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzx
 * @since 2023-04-26
 */
//@RestController
@RequestMapping("/order")
@Controller
public class ShippingOrderController {

    @Autowired
    private ShippingOrderService shippingOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 去创建订单页
     * */
    @RequestMapping("/toCreateOrder")
    public String toCreatOrder(Model model){
        model.addAttribute("shippingOrder", new ShippingOrder());
        return "order/createOrder";
    }

    @RequestMapping("/toSearchOrder")
    public String toSearchOrder(){
        return "order/queryOrder";
    }


    /**
     * 去显示某个人的所有订单页
     * */
    @RequestMapping("/toQueryOrder")
    public String toQueryOrder(Model model){
        List<ShippingOrder> shippingOrderList = shippingOrderService.listOrdersOfConsumer(true, UserAccountPackHolder.getUser().getId());
        List<ShippingOrder> shippingOrderList2 = shippingOrderService.listOrdersOfConsumer(false, UserAccountPackHolder.getUser().getId());
        shippingOrderList.addAll(shippingOrderList2);
        model.addAttribute("order_list",shippingOrderList);
        return "user/dashboard";
    }

    /**
     * 去显示某个订单的详情页
     * */
    @RequestMapping("/toSingleOrder")
    public String toSingelOrder(@RequestParam String orderId, Model model){
        ShippingOrder order = shippingOrderService.getOrderByOrderId(orderId);
        model.addAttribute("order",order);
        return "order/singleOrder";
    }

    /**
     * 去订单的物流信息页
     * */
    @GetMapping("/toOrderLogisticInfo")
    public String listLogisticsRecord(@RequestParam Integer orderId,Model model){
        List<LogisticsRecord> list = shippingOrderService.listLogisticsRecord(orderId);
        model.addAttribute("list",list);
        return "order/orderLogisticInfo";
    }


    /**
     * 创建订单
     * */
//    @BlockHandler(value = 30,method = "commonReduceDeal",aClass = CommonReduce.class) // 降级注解，1s内限制30个请求
    @PostMapping("/create")
    @ResponseBody
    public String createShippingOrder(@ModelAttribute ShippingOrder shippingOrder,
                                      @RequestParam("latestDeliveryDate")  String latestDeliveryDate,
//                                      @RequestParam("latestDeliveryTime") String latestDeliveryTime,
                                      Model model){
        UserAccountPack user = UserAccountPackHolder.getUser();
        shippingOrder.setConsumerId(user.getId());
        LocalDateTime localDateTime = LocalDateTime.parse(latestDeliveryDate + "T00:00");
        Date latestDeliveryDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        shippingOrder.setLatestDeliveryTime(latestDeliveryDateTime);
        R response = shippingOrderService.createShippingOrder(shippingOrder);
        // 可以将结果添加到模型中，以便在下一个视图中显示
        model.addAttribute("response", response);

        // 返回到适当的视图（这将取决于你的应用程序设计）
        return "success";
    }

    /**
     * 根据订单id返回订单信息
     * */
    @GetMapping("/get-order-by-order-id")
    @ResponseBody
    public R getOrderByOrderId(@RequestParam String orderId){
        return R.ok().data("order",shippingOrderService.getOrderByOrderId(orderId));
    }

    /**
     * 列出某个客户的订单
     * */
    @GetMapping("/get-consumer-orders")
    @ResponseBody
    public R getConsumerOrders(@RequestParam Boolean ifCompleted){
        return R.ok().data("order_list",shippingOrderService.listOrdersOfConsumer(ifCompleted,UserAccountPackHolder.getUser().getId()));
    }

    /**
     * 修改某个订单的信息
     * */
    @PutMapping("/update-order-by-id")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public R listLogisticsRecord(@RequestParam Integer orderId){
        return R.ok().data("logistics_record_list",shippingOrderService.listLogisticsRecord(orderId));
    }



}

