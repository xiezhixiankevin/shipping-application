package com.xzx.shippingapplication.pojo.pack;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarrierNamePack {
    /**
     * 承运商id
     */
    private Integer id;

    /**
     * 承运商名
     */
    private String name;
}
