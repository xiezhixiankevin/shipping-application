package com.xzx.shippingapplication.common.timeJob;

import com.alibaba.fastjson.JSON;
import com.xzx.shippingapplication.common.util.rabbit.ProducerMessage;
import com.xzx.shippingapplication.pojo.pack.CorrelationDataPack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * <Description> TaskJob
 * 定时任务
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.common.timeJob
 */
@Slf4j
@Component("taskJob")
public class TaskJob {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ProducerMessage producerMessage;

    /**
     * 重新发送未成功发送的消息的定时任务
     * 每小时执行一次
     * */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void checkRabbitMQMessage() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Set keys = hashOperations.keys(ProducerMessage.REDIS_HASHMAP_KEY);
        for (Object key : keys) {
            Object value = hashOperations.get(ProducerMessage.REDIS_HASHMAP_KEY, key);
            CorrelationDataPack correlationDataPack = JSON.parseObject((String) value, CorrelationDataPack.class);
            producerMessage.sendMsg(correlationDataPack.getData(),correlationDataPack.getExchangeName(),correlationDataPack.getRoutingKey());
        }
    }

}
