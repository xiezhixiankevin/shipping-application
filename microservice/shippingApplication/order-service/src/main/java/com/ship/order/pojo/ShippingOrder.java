package com.ship.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class ShippingOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 订单下单人id
     */
    private Integer consumerId;

    /**
     * 承运商id
     */
    private Integer providerId;

    /**
     * 寄件人姓名
     */
    private String senderName;

    /**
     * 寄件人地址(格式：省/市/区/详细)
     */
    private String senderAddress;

    /**
     * 寄件人手机号
     */
    private String senderPhoneNumber;

    /**
     * 收件人名字
     */
    private String receiverName;

    /**
     * 收件人地址(格式：省/市/区/详细)
     */
    private String receiverAddress;

    /**
     * 收件人手机号
     */
    private String receiverPhoneNumber;

    /**
     * 货物名
     */
    private String cargoName;

    /**
     * 货物重量
     */
    private Double cargoWeight;

    /**
     * 货物信息
     */
    private String cargoInfo;

    /**
     * 订单成交价
     */
    private Double orderRealCharge;

    /**
     * 订单下单人出价
     */
    private Double orderSenderCharge;

    /**
     * 承运商报价
     */
    private Double orderProviderCharge;

    /**
     * 预估运力
     */
    private Double estimateCapacity;

    /**
     * 最晚送达时间
     */
    private Date latestDeliveryTime;

    /**
     * 预估距离，单位km
     */
    private Double estimateDistance;

    /**
     * 订单状态，0发布，1谈判，2成交等待发车，3运输中，4送达待确认汇款，5完成
     */
    private Integer state;

    /**
     * 订单是否有效
     */
    @TableLogic
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTimestamp;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTimestamp;

    /**
     * 加急级别 目前只能选择1 2 3
     */
    private Integer urgentLevel;

    /**
     * 是否需要冷藏
     */
    private Boolean refrigerated;

    /**
     * 分配到哪个运力上 在途id
     */
    private Integer inTransitId;


}
