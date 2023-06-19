package com.xzx.shippingapplication.config.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <Description> MyMetaObjectHandler
 *设置mybatis-plis自动填充
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.config.component
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createTimestamp",new Date(),metaObject);
        this.setFieldValByName("updateTimestamp",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTimestamp",new Date(),metaObject);
    }
}