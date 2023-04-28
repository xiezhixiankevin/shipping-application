package com.xzx.shippingapplication.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    private Integer weight;

    /**
     * 0:未出发 1:在路上 2:已完成
     */
    private Integer status;

    /**
     * 包含几个订单
     */
    private Integer orderNum;

    /**
     * 对应的订单的id字符串数组，（对应id不是order_id）
     */
    private String orderIds;


}
