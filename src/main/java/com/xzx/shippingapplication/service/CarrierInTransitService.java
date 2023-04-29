package com.xzx.shippingapplication.service;

import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.pojo.CarrierInTransit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzx
 * @since 2023-04-28
 */
public interface CarrierInTransitService extends IService<CarrierInTransit> {

    boolean startTransportation(CarrierInTransit carrierInTransit);

    boolean endTransportation(CarrierInTransit carrierInTransit);

    R getInTransitWaitingInfo(Integer carrierId);

    R getInTransitInTransitInfo(Integer carrierId);

    R getInTransitFinishInfo(Integer carrierId);
}
