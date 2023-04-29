package com.xzx.shippingapplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.CarrierAircraft;
import com.xzx.shippingapplication.pojo.CarrierBigTruck;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.mapper.CarrierInTransitMapper;
import com.xzx.shippingapplication.pojo.CarrierSamllTruck;
import com.xzx.shippingapplication.pojo.pack.CarrierWaitingInfoPack;
import com.xzx.shippingapplication.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

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
    CarrierInTransitMapper carrierInTransitMapper;

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
        carrierInTransit.setBeginTime(new Date());
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
        shippingOrderService.updateOrderStateByInTransitId(carrierInTransit.getCarrierId(),ShippingOrderServiceImpl.STATE_TRANSPORT);



        return true;
    }

    /**
     * 手动到货
     * @param carrierInTransit
     * @return
     */
    @Override
    public boolean endTransportation(CarrierInTransit carrierInTransit) {
        //1.获取车车信息
        carrierInTransit = getById(carrierInTransit.getId());

        //2.到货 使用乐观锁 更新在途运力的状态
        carrierInTransit.setStatus(TRANSPORTATION_STATUS_FINISH);
        carrierInTransit.setEndTime(new Date());
        boolean success = update(carrierInTransit,
                new UpdateWrapper<CarrierInTransit>().eq("status", TRANSPORTATION_STATUS_IN_TRANSIT));
        if(!success)return false;//已经被其他线程处理了

        //3.更新运力状态 //更新运力的状态 当前所在位置 目标地 重置剩余容量
        if(carrierInTransit.getType()==TRANSPORTATION_TYPE_SMALL_TRUCK){

            CarrierSamllTruck samllTruck = new CarrierSamllTruck();
            samllTruck.setId(carrierInTransit.getTransportId());                        //运力id
            samllTruck.setStatus(TRANSPORTATION_STATUS_WAITING);                        //设置为等待状态
            samllTruck.setCityId(carrierInTransit.getEndCityId());                      //设置出发地id
            samllTruck.setTargetCityId(carrierInTransit.getBeginCityId());              //设置目的地id
            samllTruck.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_SMALL_TRUCK);    //重置剩余容量
            samllTruckService.updateById(samllTruck);
        }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_BIG_TRUCK){
            CarrierBigTruck bigTruck = new CarrierBigTruck();
            bigTruck.setId(carrierInTransit.getTransportId());
            bigTruck.setStatus(TRANSPORTATION_STATUS_WAITING);
            bigTruck.setCityId(carrierInTransit.getEndCityId());
            bigTruck.setTargetCityId(carrierInTransit.getBeginCityId());
            bigTruck.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_BIG_TRUCK);
            bigTruckService.updateById(bigTruck);
        }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_AIRCRAFT){
            CarrierAircraft aircraft = new CarrierAircraft();
            aircraft.setId(carrierInTransit.getTransportId());
            aircraft.setStatus(TRANSPORTATION_STATUS_WAITING);
            aircraft.setCityId(carrierInTransit.getEndCityId());
            aircraft.setTargetCityId(carrierInTransit.getBeginCityId());
            aircraft.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_AIRCRAFT);
            aircraftService.updateById(aircraft);
        }

        //4.更新订单表中所有涉及到的订单的状态
        shippingOrderService.updateOrderStateByInTransitId(carrierInTransit.getCarrierId(),ShippingOrderServiceImpl.STATE_ARRIVED);



        return true;
    }


    @Override
    public R getInTransitWaitingInfo(Integer carrierId) {
        QueryWrapper<CarrierInTransit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("carrier_id",carrierId);
        queryWrapper.eq("status",TRANSPORTATION_STATUS_WAITING);
        List<CarrierInTransit> list1 = list(queryWrapper);
        List<CarrierWaitingInfoPack> list=new ArrayList<>();
        for (CarrierInTransit carrierInTransit : list1) {
            CarrierWaitingInfoPack infoPack = new CarrierWaitingInfoPack();

            Integer type = carrierInTransit.getType();
            if(type==TRANSPORTATION_TYPE_SMALL_TRUCK)infoPack.setType("小货车");
            else if(type==TRANSPORTATION_TYPE_BIG_TRUCK)infoPack.setType("大货车");
            else if(type==TRANSPORTATION_TYPE_AIRCRAFT)infoPack.setType("飞机");
            infoPack.setId(carrierInTransit.getId());
            infoPack.setTransportId(carrierInTransit.getTransportId());
            infoPack.setBeginCity(ID_TO_CITY_MAP.get(carrierInTransit.getBeginCityId()));
            infoPack.setEndCity(ID_TO_CITY_MAP.get(carrierInTransit.getEndCityId()));
            infoPack.setWeight(carrierInTransit.getWeight());
            infoPack.setOrderNum(carrierInTransit.getOrderNum());
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            simpleDateFormat.format(carrierInTransit.getBeginTime());

            list.add(infoPack);
        }


        return R.ok().data("list",list);
    }
}
