package com.ship.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
public class LogisticsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应的order(订单)表的id
     */
    private Integer orderId;

    /**
     * 此条物流记录的对应的状态（如发货中，运输中）
     */
    private Integer state;

    /**
     * 内容
     */
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date createTimestamp;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTimestamp;


}
