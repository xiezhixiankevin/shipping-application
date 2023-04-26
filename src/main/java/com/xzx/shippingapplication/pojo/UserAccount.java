package com.xzx.shippingapplication.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
 * @since 2023-04-25
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
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

    public UserAccount(UserAccountPack userAccountPack) {
        setUsername(userAccountPack.getUsername());
        setPassword(userAccountPack.getPassword());
        setEmail(userAccountPack.getEmail());
        setIdentity(userAccountPack.getIdentity());
    }
}
