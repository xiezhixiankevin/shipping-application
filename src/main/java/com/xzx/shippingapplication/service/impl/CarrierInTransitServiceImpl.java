package com.xzx.shippingapplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzx.shippingapplication.pojo.CarrierAircraft;
import com.xzx.shippingapplication.pojo.CarrierBigTruck;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.mapper.CarrierInTransitMapper;
import com.xzx.shippingapplication.pojo.CarrierSamllTruck;
import com.xzx.shippingapplication.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xzx.shippingapplication.common.util.Constant.*;
import static com.xzx.shippingapplication.common.util.Constant.TRANSPORTATION_STATUS_IN_TRANSIT;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-28
 */
@Service
public class CarrierInTransitServiceImpl extends ServiceImpl<CarrierInTransitMapper, CarrierInTransit> implements CarrierInTransitService {


    @Autowired
    CarrierSamllTruckService samllTruckService;

    @Autowired
    CarrierBigTruckService bigTruckService;

    @Autowired
    CarrierAircraftService aircraftService;

    @Autowired
    ShippingOrderService shippingOrderService;

    /**
     * 手动发货
     * @param carrierInTransit
     * @return 是否发货成功
     */
    @Override
    @Transactional
    public boolean startTransportation(CarrierInTransit carrierInTransit) {
        //1.获取车车信息
        carrierInTransit = getById(carrierInTransit.getId());
        //2.发货 使用乐观锁 更新在途运力的状态
        carrierInTransit.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
        boolean success = update(carrierInTransit,
                new UpdateWrapper<CarrierInTransit>().eq("status", TRANSPORTATION_STATUS_WAITING));
        if(!success)return false;
        //3.更新运力状态
        if(carrierInTransit.getType()==TRANSPORTATION_TYPE_SMALL_TRUCK){
            CarrierSamllTruck samllTruck = new CarrierSamllTruck();
            samllTruck.setId(carrierInTransit.getTransportId());
            samllTruck.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
            samllTruckService.updateById(samllTruck);
        }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_BIG_TRUCK){
            CarrierBigTruck bigTruck = new CarrierBigTruck();
            bigTruck.setId(carrierInTransit.getTransportId());
            bigTruck.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
            bigTruckService.updateById(bigTruck);
        }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_AIRCRAFT){
            CarrierAircraft aircraft = new CarrierAircraft();
            aircraft.setId(carrierInTransit.getTransportId());
            aircraft.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
            aircraftService.updateById(aircraft);
        }

        //4.更新订单表中所有涉及到的订单的状态
//        shippingOrderService.



        return true;
    }
}
