package com.xzx.shippingapplication.common.timeJob;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.shippingapplication.common.util.rabbit.ProducerMessage;
import com.xzx.shippingapplication.pojo.CarrierAircraft;
import com.xzx.shippingapplication.pojo.CarrierBigTruck;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.xzx.shippingapplication.pojo.CarrierSamllTruck;
import com.xzx.shippingapplication.pojo.pack.CorrelationDataPack;
import com.xzx.shippingapplication.service.CarrierAircraftService;
import com.xzx.shippingapplication.service.CarrierBigTruckService;
import com.xzx.shippingapplication.service.CarrierInTransitService;
import com.xzx.shippingapplication.service.CarrierSamllTruckService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.xzx.shippingapplication.common.util.Constant.*;

/**
 * <Description> TaskJob
 * 定时任务
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.common.timeJob
 */
@Slf4j
@Component("taskJob")
@EnableAsync
public class TaskJob {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ProducerMessage producerMessage;

    @Autowired
    CarrierInTransitService carrierInTransitService;

    @Autowired
    CarrierSamllTruckService samllTruckService;

    @Autowired
    CarrierBigTruckService bigTruckService;

    @Autowired
    CarrierAircraftService aircraftService;

    /**
     * 重新发送未成功发送的消息的定时任务
     * 每小时执行一次
     * */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Async
    public void checkRabbitMQMessage() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Set keys = hashOperations.keys(ProducerMessage.REDIS_HASHMAP_KEY);
        for (Object key : keys) {
            Object value = hashOperations.get(ProducerMessage.REDIS_HASHMAP_KEY, key);
            CorrelationDataPack correlationDataPack = JSON.parseObject((String) value, CorrelationDataPack.class);
            producerMessage.sendMsg(correlationDataPack.getData(),correlationDataPack.getExchangeName(),correlationDataPack.getRoutingKey());
        }
    }

    /**
     * 发货
     * 周期从16到19小时 每天执行3次 每小时执行一次
     */
//    @Scheduled(cron = "1 0 16-19 * * ? ")
//    @Async
//    public void deliver() {
//        //发货 查看未出发的在途运力
//        QueryWrapper<CarrierInTransit> transitQueryWrapper = new QueryWrapper<>();
//        transitQueryWrapper.eq("status",TRANSPORTATION_STATUS_WAITING)
//                .lt("begin_time",new Date());
//        List<CarrierInTransit> list = carrierInTransitService.list(transitQueryWrapper);
//
//        //没有未出发的运力 直接返回
//        if(list.isEmpty())return;
//        //发车！ 将相应的运力状态改为在路上
//        ArrayList<CarrierSamllTruck> carrierSamllTruckList = new ArrayList<>();
//        ArrayList<CarrierBigTruck> carrierBigTruckList = new ArrayList<>();
//        ArrayList<CarrierAircraft> carrierAircraftList = new ArrayList<>();
//        for (CarrierInTransit carrierInTransit : list) {
//            carrierInTransit.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
//            if(carrierInTransit.getType()==TRANSPORTATION_TYPE_SMALL_TRUCK){
//                CarrierSamllTruck samllTruck = new CarrierSamllTruck();
//                samllTruck.setId(carrierInTransit.getTransportId());
//                samllTruck.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
//                carrierSamllTruckList.add(samllTruck);
//            }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_BIG_TRUCK){
//                CarrierBigTruck bigTruck = new CarrierBigTruck();
//                bigTruck.setId(carrierInTransit.getTransportId());
//                bigTruck.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
//                carrierBigTruckList.add(bigTruck);
//            }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_AIRCRAFT){
//                CarrierAircraft aircraft = new CarrierAircraft();
//                aircraft.setId(carrierInTransit.getTransportId());
//                aircraft.setStatus(TRANSPORTATION_STATUS_IN_TRANSIT);
//                carrierAircraftList.add(aircraft);
//            }
//        }
//
//        //更新数据库
//        carrierInTransitService.updateBatchById(list);
//        if(!carrierSamllTruckList.isEmpty())samllTruckService.updateBatchById(carrierSamllTruckList);
//        if(!carrierBigTruckList.isEmpty())bigTruckService.updateBatchById(carrierBigTruckList);
//        if(!carrierAircraftList.isEmpty())aircraftService.updateBatchById(carrierAircraftList);
//
//
//
//    }



    /**
     *
     *  检查在途中的车车是否已经到达
     *  每小时执行一次
     */

//    @Scheduled(cron = "1 0 0/1 * * ? ")
//    @Async
//    public void checkArrive(){
//        //查询在途运力
//        QueryWrapper<CarrierInTransit> transitQueryWrapper = new QueryWrapper<>();
//        transitQueryWrapper .eq("status",TRANSPORTATION_STATUS_IN_TRANSIT)
//                            .lt("end_time",new Date());
//        List<CarrierInTransit> list = carrierInTransitService.list(transitQueryWrapper);
//        //没有到达的运力 直接返回
//        if(list.isEmpty())return;
//
//        //已经到达 更新在途表的finish字段
//
//        ArrayList<CarrierSamllTruck> carrierSamllTruckList = new ArrayList<>();
//        ArrayList<CarrierBigTruck> carrierBigTruckList = new ArrayList<>();
//        ArrayList<CarrierAircraft> carrierAircraftList = new ArrayList<>();
//
//        for (CarrierInTransit carrierInTransit : list) {
//            carrierInTransit.setStatus(TRANSPORTATION_STATUS_FINISH);
//            if(carrierInTransit.getType()==TRANSPORTATION_TYPE_SMALL_TRUCK){
//                //更新运力的状态 当前所在位置 目标地 重置剩余容量
//                CarrierSamllTruck samllTruck = new CarrierSamllTruck();
//                samllTruck.setId(carrierInTransit.getTransportId());                        //运力id
//                samllTruck.setStatus(TRANSPORTATION_STATUS_WAITING);                        //设置为等待状态
//                samllTruck.setCityId(carrierInTransit.getEndCityId());                      //设置出发地id
//                samllTruck.setTargetCityId(carrierInTransit.getBeginCityId());              //设置目的地id
//                samllTruck.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_SMALL_TRUCK);    //重置剩余容量
//                carrierSamllTruckList.add(samllTruck);
//            }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_BIG_TRUCK){
//                //更新运力的状态 当前所在位置 目标地 重置剩余容量
//                CarrierBigTruck bigTruck = new CarrierBigTruck();
//                bigTruck.setId(carrierInTransit.getTransportId());
//                bigTruck.setStatus(TRANSPORTATION_STATUS_WAITING);
//                bigTruck.setCityId(carrierInTransit.getEndCityId());
//                bigTruck.setTargetCityId(carrierInTransit.getBeginCityId());
//                bigTruck.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_BIG_TRUCK);
//                carrierBigTruckList.add(bigTruck);
//            }else if(carrierInTransit.getType()==TRANSPORTATION_TYPE_AIRCRAFT){
//                //更新运力的状态 当前所在位置 目标地 重置剩余容量
//                CarrierAircraft aircraft = new CarrierAircraft();
//                aircraft.setId(carrierInTransit.getTransportId());
//                aircraft.setStatus(TRANSPORTATION_STATUS_WAITING);
//                aircraft.setCityId(carrierInTransit.getEndCityId());
//                aircraft.setTargetCityId(carrierInTransit.getBeginCityId());
//                aircraft.setResidualCapacity(TRANSPORTATION_MAX_CAPACITY_AIRCRAFT);
//                carrierAircraftList.add(aircraft);
//            }
//        }
//
//        //更新数据库
//        carrierInTransitService.updateBatchById(list);
//        if(!carrierSamllTruckList.isEmpty())samllTruckService.updateBatchById(carrierSamllTruckList);
//        if(!carrierBigTruckList.isEmpty())bigTruckService.updateBatchById(carrierBigTruckList);
//        if(!carrierAircraftList.isEmpty())aircraftService.updateBatchById(carrierAircraftList);
//
//    }
}
