package com.xzx.shippingapplication.controller.carrier;


import cn.hutool.core.bean.BeanUtil;
import cn.itcast.feign.common.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.util.UserAccountPackHolder;
import com.xzx.shippingapplication.pojo.Carrier;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.pojo.pack.CarrierNamePack;
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
    @GetMapping("/get-all-carrier-name")
    public R getAllCarrier(){
        List<Carrier> list = carrierService.list(new QueryWrapper<Carrier>());
        List<CarrierNamePack> carrierNamePacks = BeanUtil.copyToList(list, CarrierNamePack.class);
        return R.ok().data("carrierName",carrierNamePacks);
    }

    /**
     * 承运商手动发货
     *
     * 参数需要的：inTransitId
     * @return
     */
    @PostMapping("/start-transportation")
    public R startTransportation(@RequestBody CarrierInTransit carrierInTransit){
        boolean res=carrierInTransitService.startTransportation(carrierInTransit);
        if(res)return R.ok().message("发车成功");
        return R.error().message("已经发车了，请刷新");
    }

    /**
     * 承运商手动到货
     *
     *  参数需要的：inTransitId
     * @return
     */
    @PostMapping("/end-transportation")
    public R endTransportation(@RequestBody CarrierInTransit carrierInTransit){
        boolean res=carrierInTransitService.endTransportation(carrierInTransit);
        if(res)return R.ok().message("该车到货成功");
        return R.error().message("该车到货失败，请重试");
    }


    /**
     * 在途运力(还未发车)查询
     * @return
     */
    @GetMapping("/get-transportation-waiting-info")
    public R getInTransitWaitingInfo(){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();

        return carrierInTransitService.getInTransitWaitingInfo(carrierId);
    }


    /**
     * 在途运力(运输中)查询
     * @return
     */
    @GetMapping("/get-transportation-in-transit-info")
    public R getInTransitInTransitInfo(){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        return carrierInTransitService.getInTransitInTransitInfo(carrierId);
    }


    /**
     * 在途运力(已结束)查询
     * @return
     */
    @GetMapping("/get-transportation-finish-info")
    public R getInTransitFinishInfo(){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        return carrierInTransitService.getInTransitFinishInfo(carrierId);
    }


    @GetMapping("/get-carrier-info")
    public R getCarrierInfo(){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        return carrierInTransitService.getCarrierInfo(carrierId);
    }



//    /**
//     * 查看运力池详细信息
//     * @return R
//     */
//    //TODO
//    @GetMapping("/get-all-transportation-info")
//    public R getAllTransportationInfo(){
//
//        //TODO
//        System.out.println("进来了");
//        return carrierService.getAllTransportationInfo();
//    }


    //TODO
    //添加运力

    //TODO
    //删除运力


}
