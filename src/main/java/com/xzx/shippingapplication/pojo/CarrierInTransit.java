package com.xzx.shippingapplication.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2023-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarrierInTransit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 是否已经完成 1 2 3
     */
    private Integer status;

    /**
     * 包含几个订单
     */
    private Integer orderNum;


}
