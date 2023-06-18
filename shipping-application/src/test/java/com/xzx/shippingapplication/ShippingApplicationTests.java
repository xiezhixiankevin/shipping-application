package com.xzx.shippingapplication;

import com.xzx.shippingapplication.common.util.rabbit.ProducerMessage;
import com.xzx.shippingapplication.config.RabbitConfig;
import com.xzx.shippingapplication.pojo.ShippingOrder;
import com.xzx.shippingapplication.pojo.UserAccount;
import com.xzx.shippingapplication.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class ShippingApplicationTests {

    @Autowired
    ProducerMessage producerMessage;

    @Before
    public void init() {
        log.info("开始测试...");
    }

    @After
    public void after() {
        log.info("测试结束...");
    }

    @Test
    void contextLoads() throws Exception {

    }

    @Autowired
    UserAccountService userAccountService;

    @Test
    void passwordCoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1");
        System.out.println(encode);

        String encode1 = bCryptPasswordEncoder.encode("123");
        System.out.println(encode1);
    }

}
