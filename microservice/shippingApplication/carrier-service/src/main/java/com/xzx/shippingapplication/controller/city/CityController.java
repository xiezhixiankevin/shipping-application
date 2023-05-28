package com.xzx.shippingapplication.controller.city;


import cn.itcast.feign.common.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.pojo.AreaProvincialCapital;
import com.xzx.shippingapplication.service.AreaProvincialCapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    AreaProvincialCapitalService areaProvincialCapitalService;

    /**
     *     获取所有城市信息 id 和 名称
     */
    @GetMapping("/All")
    public R getAllCarrier(){
        List<AreaProvincialCapital> list = areaProvincialCapitalService.list(new QueryWrapper<AreaProvincialCapital>());
        return R.ok().data("city",list);
    }



}
