package com.xzx.shippingapplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.pojo.*;
import com.xzx.shippingapplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.xzx.shippingapplication.common.util.Constant.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-27
 */
@Service
public class CarrierServiceImpl implements CarrierService {

    @Autowired
    CarrierSamllTruckService samllTruckService;

    @Autowired
    CarrierBigTruckService bigTruckService;

    @Autowired
    CarrierAircraftService aircraftService;


    @Autowired
    CarrierInTransitService inTransitService;


    @Override
    @Transactional
    public void allocation(ShippingOrder order) {
        System.out.println(order);

        //根据承运商+出发地+目的地+是否加急+是否冷藏+当前剩余容量=查看车辆信息
        Integer carrierId = order.getProviderId();
        Integer beginCityId = CITY_TO_ID_MAP.get(order.getSenderAddress());
        Integer endCityId = CITY_TO_ID_MAP.get(order.getReceiverAddress());
        Integer urgentLevel = order.getUrgentLevel();
        Boolean refrigerated = order.getRefrigerated();
        Double weight = order.getCargoWeight();


        if(urgentLevel==2){//分配小货车
            smallTruckAllocation(carrierId, beginCityId, endCityId, refrigerated, weight);

        }else if(urgentLevel==1){//分配大货车
            bigTruckAllocation(carrierId, beginCityId, endCityId, refrigerated, weight);

        }else if(urgentLevel==3){//分配飞机
            aircraftAllocation(carrierId, beginCityId, endCityId, refrigerated, weight);
        }

    }


    private <T> void smallTruckAllocation(Integer carrierId, Integer beginCityId, Integer endCityId, Boolean refrigerated, Double weight) {
        CarrierSamllTruck carrierSamllTruck = new CarrierSamllTruck();
        carrierSamllTruck.setCarrierId(carrierId);
        carrierSamllTruck.setStatus(TRANSPORTATION_STATUS_WAITING);
        carrierSamllTruck.setCityId(beginCityId);
        carrierSamllTruck.setRefrigerated(refrigerated ?TRANSPORTATION_REFRIGERATED:TRANSPORTATION_NOT_REFRIGERATED);
        carrierSamllTruck.setTargetCityId(endCityId);
        Wrapper<CarrierSamllTruck> wrapper = new QueryWrapper<>(carrierSamllTruck);
        List<CarrierSamllTruck> list = samllTruckService.list(wrapper);
        //选第一辆有剩余空间的
//            CarrierSamllTruck samllTruck;
        for (CarrierSamllTruck truck : list) {
            if(truck.getResidualCapacity()>= weight){
                carrierSamllTruck=truck;
                break;
            }
        }
        //将这个订单分配给这辆车
        //更新重量
        carrierSamllTruck.setResidualCapacity(carrierSamllTruck.getResidualCapacity()- weight);
        samllTruckService.updateById(carrierSamllTruck);

        //装车
        AddOrUpdateIntransitive(carrierId, beginCityId, endCityId, weight, carrierSamllTruck,TRANSPORTATION_TYPE_SMALL_TRUCK);

    }

    private void bigTruckAllocation(Integer carrierId, Integer beginCityId, Integer endCityId, Boolean refrigerated, Double weight) {
        CarrierBigTruck carrierBigTruck = new CarrierBigTruck();
        carrierBigTruck.setCarrierId(carrierId);
        carrierBigTruck.setStatus(TRANSPORTATION_STATUS_WAITING);
        carrierBigTruck.setCityId(beginCityId);
        carrierBigTruck.setRefrigerated(refrigerated ?TRANSPORTATION_REFRIGERATED:TRANSPORTATION_NOT_REFRIGERATED);
        carrierBigTruck.setTargetCityId(endCityId);
        Wrapper<CarrierBigTruck> wrapper = new QueryWrapper<>(carrierBigTruck);
        List<CarrierBigTruck> list = bigTruckService.list(wrapper);
        //选第一辆有剩余空间的
//            CarrierSamllTruck samllTruck;
        for (CarrierBigTruck truck : list) {
            if(truck.getResidualCapacity()>= weight){
                carrierBigTruck=truck;
                break;
            }
        }
        //将这个订单分配给这辆车
        //更新重量
        carrierBigTruck.setResidualCapacity(carrierBigTruck.getResidualCapacity()- weight);
        bigTruckService.updateById(carrierBigTruck);

        //装车
        AddOrUpdateIntransitive(carrierId, beginCityId, endCityId, weight, carrierBigTruck,TRANSPORTATION_TYPE_BIG_TRUCK);
    }

    private void aircraftAllocation(Integer carrierId, Integer beginCityId, Integer endCityId, Boolean refrigerated, Double weight) {
        CarrierAircraft carrierAircraft = new CarrierAircraft();
        carrierAircraft.setCarrierId(carrierId);
        carrierAircraft.setStatus(TRANSPORTATION_STATUS_WAITING);
        carrierAircraft.setCityId(beginCityId);
        carrierAircraft.setRefrigerated(refrigerated ?TRANSPORTATION_REFRIGERATED:TRANSPORTATION_NOT_REFRIGERATED);
        carrierAircraft.setTargetCityId(endCityId);
        Wrapper<CarrierAircraft> wrapper = new QueryWrapper<>(carrierAircraft);
        List<CarrierAircraft> list = aircraftService.list(wrapper);
        //选第一辆有剩余空间的
//            CarrierSamllTruck samllTruck;
        for (CarrierAircraft truck : list) {
            if(truck.getResidualCapacity()>= weight){
                carrierAircraft=truck;
                break;
            }
        }
        //将这个订单分配给这辆车
        //更新重量
        carrierAircraft.setResidualCapacity(carrierAircraft.getResidualCapacity()- weight);
        aircraftService.updateById(carrierAircraft);

        //装车
        AddOrUpdateIntransitive(carrierId, beginCityId, endCityId, weight, carrierAircraft,TRANSPORTATION_TYPE_AIRCRAFT);

    }



    private <T extends CarrierTransportation> void AddOrUpdateIntransitive(Integer carrierId, Integer beginCityId, Integer endCityId, Double weight, T truck, int type) {
        //生成在途记录
        CarrierInTransit carrierInTransit = new CarrierInTransit();
        carrierInTransit.setType(type);//要改的
        carrierInTransit.setTransportId(truck.getId());
        carrierInTransit.setStatus(TRANSPORTATION_STATUS_WAITING);
        Wrapper<CarrierInTransit> carrierInTransitWrapper=new QueryWrapper<>(carrierInTransit);
        CarrierInTransit inTransit = inTransitService.getOne(carrierInTransitWrapper);

        //如果这个订单是这辆车的第一单，就添加数据
        if(inTransit==null){
            Date beginTime = new Date();
            beginTime.setTime(getStartOfDay(new Date()).getTime() + 1000L * 60 * 60 * truck.getBeginTime());
            Date endTime = new Date();
            endTime.setTime(beginTime.getTime() + 1000L * 60 * 60 * truck.getHoleTime());

            carrierInTransit.setCarrierId(carrierId);
            carrierInTransit.setBeginCityId(beginCityId);
            carrierInTransit.setEndCityId(endCityId);
            carrierInTransit.setBeginTime(beginTime);
            carrierInTransit.setEndTime(endTime);
            carrierInTransit.setWeight(weight);
            carrierInTransit.setOrderNum(1);

            inTransitService.save(carrierInTransit);
        }
        //不是第一单，更新数据
        else{
            inTransit.setWeight(weight +inTransit.getWeight());
            inTransit.setOrderNum(1+inTransit.getOrderNum());

            inTransitService.updateById(inTransit);
        }
    }


    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

}
