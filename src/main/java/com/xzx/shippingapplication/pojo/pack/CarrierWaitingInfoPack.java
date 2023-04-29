package com.xzx.shippingapplication.pojo.pack;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xzx
 * @since 2023-04-29
 */
@Data
public class CarrierWaitingInfoPack implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;


    /**
     * 运力类型 1:小货车 2:大货车3:飞机
     */
    private String type;

    /**
     * 运力id
     */
    private Integer transportId;

    /**
     * 出发城市
     */
    private String beginCity;

    /**
     * 目标城市
     */
    private String endCity;

    /**
     * 出发时间
     */
    private String beginTime;

    /**
     * 预计到达时间
     */
    private String endTime;

    /**
     * 该运输工具所装货物的重量
     */
    private Double weight;


    /**
     * 包含几个订单
     */
    private Integer orderNum;


}
