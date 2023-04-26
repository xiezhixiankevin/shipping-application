package com.xzx.shippingapplication.controller;


import com.xzx.shippingapplication.annota.BlockHandler;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.service.ShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;

    @PostMapping("/create")
    public R createShippingOrder(@RequestBody ShippingOrder shippingOrder){
        return service.createShippingOrder(shippingOrder);
    }

    /**
     * 以下是基于切面和redis的限流示例
     * */
    @BlockHandler(value = 2,method = "reduceFinal") // 同一个类的降级方法不用指定class
    @RequestMapping("reduce")
    public String reduce(@RequestParam("s") String s){
        String key="BlockHandler.com.mabo.controller.AddEventController.reduce";
        Object o = redisTemplate.opsForValue().get(key);
        return "reduce，参数"+s+"   请求次数:"+(o==null?"0":(String) o);
    }


    public String reduceFinal(String s){
        String key="BlockHandler.com.mabo.controller.AddEventController.reduce";
        Object o = redisTemplate.opsForValue().get(key);
        return "进入降级方法，不执行原方法，参数"+s+"   请求次数:"+(o==null?"0":(String) o);
    }

}

