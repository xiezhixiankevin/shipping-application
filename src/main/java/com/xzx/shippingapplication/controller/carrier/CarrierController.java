package com.xzx.shippingapplication.controller.carrier;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.Carrier;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.service.CarrierInTransitService;
import com.xzx.shippingapplication.service.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrier")
public class CarrierController {

    @Autowired
    CarrierService carrierService;

    @Autowired
    CarrierInTransitService carrierInTransitService;

    /**
     * 获取承运商公司详情
     * @return R
     */
    @GetMapping("/All")
    public R getAllCarrier(){
        List<Carrier> list = carrierService.list(new QueryWrapper<Carrier>());
        return R.ok().data("carrier",list);
    }

    /**
     * 承运商手动发车
     * 参数需要的：inTransitId
     * @return
     */
    @PostMapping("/start-transportation")
    public R startTransportation(@RequestBody CarrierInTransit carrierInTransit){
        boolean res=carrierInTransitService.startTransportation(carrierInTransit);
        if(res)return R.ok().message("发车成功");
        return R.error().message("发车失败，请重试");
    }

    //承运商手动到货

    //查看运力池详细信息

    //待发货运力查询

    //通过承运商id 返回 运力详情

    //添加运力

    //删除运力


}
