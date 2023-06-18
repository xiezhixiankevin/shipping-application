package com.xzx.shippingapplication.controller.degradation;

import com.xzx.shippingapplication.common.R;
import org.springframework.stereotype.Component;

/**
 * <Description> CommonReduce
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.controller.degradation
 */
@Component
public class CommonReduce {

    public R commonReduceDeal(){
        return R.error().message("系统负载过高，请稍后重试.....");
    }


}
