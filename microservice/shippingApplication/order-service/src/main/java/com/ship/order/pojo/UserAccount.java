package com.ship.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    /**
     * 身份，0货主，1承运商，2管理员
     */
    private Integer identity;

    /**
     * 此条记录状态, 0: 已删除, 1: 可用
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
     * 承运商id(如果是承运商的话)
     */
    private Integer carrierId;

    public UserAccount(UserAccountPack userAccountPack) {
        setUsername(userAccountPack.getUsername());
        setPassword(userAccountPack.getPassword());
        setEmail(userAccountPack.getEmail());
        setIdentity(userAccountPack.getIdentity());
    }


}
