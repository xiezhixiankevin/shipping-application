package com.xzx.shippingapplication.mapper;

import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzx.shippingapplication.pojo.pack.CarrierInTransitPack;
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

//    @Select("select * from #{tableName} a,carrier_in_transit b where b.transport_id=a.id and b.`status`=")
//    public List<CarrierInTransitPack> getInfo(Integer carrierId, int type,String tableName);
}
