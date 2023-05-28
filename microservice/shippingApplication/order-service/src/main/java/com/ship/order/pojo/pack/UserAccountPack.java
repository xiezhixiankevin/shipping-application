package com.ship.order.pojo.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <Description> UserAccountPack
 *
 * @author 26802
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccountPack {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Integer identity;

    private String code;

    private String token;

    private Integer carrierId;

}
