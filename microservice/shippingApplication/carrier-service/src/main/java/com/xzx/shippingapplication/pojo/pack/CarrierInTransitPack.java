package com.xzx.shippingapplication.pojo.pack;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarrierInTransitPack implements Serializable {



    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车牌号
     */
    private String licensePlateNumber;



    /**
     * 承运商id
     */
    private Integer carrierId;

    /**
     * 运力类型 1:小货车 2:大货车3:飞机
     */
    private Integer type;

    /**
     * 运力id
     */
    private Integer transportId;

    /**
     * 出发城市
     */
    private Integer beginCityId;

    /**
     * 目标城市
     */
    private Integer endCityId;

    /**
     * 出发时间
     */
    private Date beginTime;

    /**
     * 预计到达时间
     */
    private Date endTime;

    /**
     * 该运输工具所装货物的重量
     */
    private Double weight;

    /**
     * 0:未出发 1:在路上 2:已完成
     */
    private Integer status;

    /**
     * 包含几个订单
     */
    private Integer orderNum;



}
