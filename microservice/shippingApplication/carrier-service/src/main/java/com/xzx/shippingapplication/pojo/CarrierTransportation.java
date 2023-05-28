package com.xzx.shippingapplication.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public abstract class CarrierTransportation {


    private Integer id;

    /**
     * 当前状态：1：等待发车 2：正在路上 3：其他

     */
    private Integer status;

    /**
     * 当前所在城市id
     */
    private Integer cityId;

    /**
     * 车牌号
     */
    private String licensePlateNumber;

    /**
     * 是否有冷藏功能:0:没有 1:有
     */
    private Integer refrigerated;

    /**
     * 最大容量
     */
    private Double maxCapacity;

    /**
     * 所属承运商

     */
    private Integer carrierId;

    /**
     * 固定往返城市（目标城市）
     */
    private Integer targetCityId;

    /**
     * 当前剩余容量
     */
    private Double residualCapacity;

    /**
     * 全程所需时间 单位：小时h
     */
    private Integer holeTime;

    /**
     * 这辆车每天固定的发车时间 24小时制 单位：小时

     */
    private Integer beginTime;

}
