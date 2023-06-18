package com.xzx.shippingapplication.controller.carrier;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.UserAccountPackHolder;
import com.xzx.shippingapplication.pojo.Carrier;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.pojo.pack.CarrierNamePack;
import com.xzx.shippingapplication.service.CarrierInTransitService;
import com.xzx.shippingapplication.service.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/carrier")
public class CarrierController {

    @Autowired
    CarrierService carrierService;

    @Autowired
    CarrierInTransitService carrierInTransitService;

    /**
     * 去承运商列表页
     * @return R
     */
    @GetMapping("/toCarrierList")
    public String getAllCarrier(Model model){
        List<Carrier> list = carrierService.list(new QueryWrapper<Carrier>());
        List<CarrierNamePack> carrierNamePacks = BeanUtil.copyToList(list, CarrierNamePack.class);
        model.addAttribute("carrierNames",carrierNamePacks);
        return "/carrier/carrierList";
    }

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
     * 去往在途运力查询页
     * @param type:1还未发出 2运输中 3已结束
     */
    @GetMapping("/toTransportationInfo")
    public String getInTransitWaitingInfo(@RequestParam Integer type,Model model){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        R info = null;
        if (type == 1){
            info = carrierInTransitService.getInTransitWaitingInfo(carrierId);
        }else if(type == 2){
            info = carrierInTransitService.getInTransitInTransitInfo(carrierId);
        }else {
            info = carrierInTransitService.getInTransitFinishInfo(carrierId);
        }
        model.addAttribute("result",info);
        return "carrier/transportation";
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

    /**
     * 去往carrier信息页
     */
    @GetMapping("/toTransportationInfo")
    public String toCarrierInfo(Model model){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        R carrierInfo = carrierInTransitService.getCarrierInfo(carrierId);
        model.addAttribute("result",carrierInfo);
        return "carrier/carrierInfo";
    }


    @GetMapping("/get-carrier-info")
    public R getCarrierInfo(){
        Integer carrierId = UserAccountPackHolder.getUser().getCarrierId();
        return carrierInTransitService.getCarrierInfo(carrierId);
    }





}
