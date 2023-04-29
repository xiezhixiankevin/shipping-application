package com.xzx.shippingapplication.controller.carrier;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.Carrier;
import com.xzx.shippingapplication.service.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carrier")
public class CarrierController {

    @Autowired
    CarrierService carrierService;

    //获取承运商公司详情
    @GetMapping("/All")
    public R getAllCarrier(){
        System.out.println("all");
        List<Carrier> list = carrierService.list(new QueryWrapper<Carrier>());
        System.out.println(list);
        return R.ok().data("carrier",list);
    }

    //查看运力池详细信息
    //返回 运力详情

    //添加运力

    //删除运力


}
