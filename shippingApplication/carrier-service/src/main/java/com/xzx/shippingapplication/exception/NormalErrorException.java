package com.xzx.shippingapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <Description> LoginErrorException
 *
 * @author 26802
 * @version 1.0
 */
@Data
@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor   //生成无参数构造
public class NormalErrorException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

}
