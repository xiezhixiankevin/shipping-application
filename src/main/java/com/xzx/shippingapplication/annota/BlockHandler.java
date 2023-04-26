package com.xzx.shippingapplication.annota;

/**
 * <Description> BlockHandler
 *在当前修饰的方法前后执行其他的方法
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.annota
 */
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BlockHandler {
    //默认timeOut事件端内可以请求的次数为100次
    int value() default 100;
    Class aClass() default Class.class;
    String method() ;
    //默认缓存事件为1s
    int timeOut() default 1;
}
