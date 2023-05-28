package com.ship.order.controller.order;


import cn.itcast.feign.common.R;
import cn.itcast.feign.pojo.LogisticsRecord;
import cn.itcast.feign.pojo.ShippingOrder;
import cn.itcast.feign.pojo.pack.UserAccountPack;
import cn.itcast.feign.util.UserAccountPackHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ship.order.service.ShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @HystrixCommand(
            groupKey = "group-create",
            fallbackMethod = "codeFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"), // 信号量隔离，因为业务方法用了ThreadLocal
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"), //超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="50"),//触发熔断最小请求数量
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="30"),//触发熔断的错误占比阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="3000"),//熔断器回复时间
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value="300"),// 单机最高并发
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value="100")// fallback单机最高并发
            }
    )
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
    public R getConsumerOrders(@RequestParam Boolean ifCompleted){
        return R.ok().data("order_list",shippingOrderService.listOrdersOfConsumer(ifCompleted,UserAccountPackHolder.getUser().getId()));
    }

    /**
     * 修改某个订单的信息
     * */
    @HystrixCommand(
            groupKey = "group-update",
            fallbackMethod = "codeFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"), // 信号量隔离，因为业务方法用了ThreadLocal
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"), //超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="50"),//触发熔断最小请求数量
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="30"),//触发熔断的错误占比阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="3000"),//熔断器回复时间
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value="300"),// 单机最高并发
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value="100")// fallback单机最高并发
            }
    )
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
    @HystrixCommand(
            groupKey = "group-add-info",
            fallbackMethod = "codeFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"), // 信号量隔离，因为业务方法用了ThreadLocal
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"), //超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="50"),//触发熔断最小请求数量
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="30"),//触发熔断的错误占比阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="3000"),//熔断器回复时间
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value="300"),// 单机最高并发
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value="100")// fallback单机最高并发
            }
    )
    @PostMapping("/add-logistics-record-by-id")
    public R addLogisticsRecordById(@RequestBody LogisticsRecord logisticsRecord){

       if(shippingOrderService.addLogisticsRecord(logisticsRecord)){
           return R.ok().message("物流信息更新成功...");
       }else {
           return R.error().message("抱歉物流信息更新失败，请重试...");
       }
    }


    /**
     * 更新订单表涉及订单状态
     * */
    @PostMapping("/update-orderState-by-transitId")
    public void updateOrderStateByInTransitId(@RequestParam Integer inTransitId, @RequestParam Integer state){
        shippingOrderService.updateOrderStateByInTransitId(inTransitId,state);
    }


    /**
     * 获取订单的所有物流信息，默认时间排序
     * */
    @GetMapping("/list-logistics-record")
    public R listLogisticsRecord(@RequestParam Integer orderId){
        return R.ok().data("logistics_record_list",shippingOrderService.listLogisticsRecord(orderId));
    }

    public R codeFallBack(@RequestParam String email){
        return R.ok().message("系统限流中，请稍后重试。。。。");
    }





}

