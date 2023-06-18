package com.xzx.shippingapplication.pojo.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
