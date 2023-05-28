package com.xzx.shippingapplication.mapper;

import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzx.shippingapplication.pojo.pack.CarrierInTransitPack;
import com.xzx.shippingapplication.pojo.pack.CarrierInfoPack;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xzx
 * @since 2023-04-29
 */

@Repository
public interface CarrierInTransitMapper extends BaseMapper<CarrierInTransit> {
    @Select(" SELECT a.smallTrunkWaiting,b.smallTrunkInTransit,c.bigTrunkWaiting,d.bigTrunkInTransit,e.aircraftWaiting,f.aircraftInTransit\n" +
            " from \n" +
            "(SELECT COUNT(*) as smallTrunkWaiting from carrier_samll_truck WHERE carrier_id=#{carrierId} and  `status`=1) a,\n" +
            "(SELECT COUNT(*) as smallTrunkInTransit from carrier_samll_truck WHERE carrier_id=#{carrierId} and  `status`=2) b,\n" +
            "(SELECT COUNT(*) as bigTrunkWaiting from carrier_big_truck WHERE carrier_id=#{carrierId} and  `status`=1) c,\n" +
            "(SELECT COUNT(*) as bigTrunkInTransit from carrier_big_truck WHERE carrier_id=#{carrierId} and  `status`=2) d,\n" +
            "(SELECT COUNT(*) as aircraftWaiting from carrier_aircraft WHERE carrier_id=#{carrierId} and  `status`=1) e,\n" +
            "(SELECT COUNT(*) as aircraftInTransit from carrier_aircraft WHERE carrier_id=#{carrierId} and  `status`=2) f\n")
    CarrierInfoPack getCarrierInfo(Integer carrierId);

//    @Select("select * from #{tableName} a,carrier_in_transit b where b.transport_id=a.id and b.`status`=")
//    public List<CarrierInTransitPack> getInfo(Integer carrierId, int type,String tableName);
}
