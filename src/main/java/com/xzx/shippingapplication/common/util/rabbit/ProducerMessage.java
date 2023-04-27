package com.xzx.shippingapplication.common.util.rabbit;

/**
 * <Description> ProducerMessage
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.common.util.rabbit
 */
import com.alibaba.fastjson.JSON;
import com.xzx.shippingapplication.pojo.pack.CorrelationDataPack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * 生产者
 */
@Component
@Slf4j
public class ProducerMessage implements  RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnCallback{

    private RabbitTemplate rabbitTemplate;

    public static final String REDIS_HASHMAP_KEY = "rabbitMq-shippingOrder-message";

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    public ProducerMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this::confirm); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setReturnCallback(this::returnedMessage);
        rabbitTemplate.setMandatory(true);
    }

    public void  sendMsg (Object content,String exchangeName,String routingKey){
        String jsonString = JSON.toJSONString(content);
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());//？？

        // 本地缓存消息
        String value = JSON.toJSONString(new CorrelationDataPack(correlationId, exchangeName, routingKey, jsonString));
        redisTemplate.opsForHash().put(REDIS_HASHMAP_KEY,correlationId.getId(),value);

        rabbitTemplate.convertAndSend(exchangeName,routingKey,jsonString,correlationId);

        // 刚刚发送消息出去，避免异步的ConfirmCallback由于资源关闭而出现错误
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 消息发送到队列中，进行消息确认
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(" 消息确认的id： " + correlationData.getId());

        if(ack){
            log.info("消息发送成功");
            //发送成功 删除redis数据库存的消息
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.delete(REDIS_HASHMAP_KEY,correlationData.getId());// 从map删除一个K-V对
        }else{
            log.info("消息发送失败：id "+ correlationData.getId() +"消息发送失败的原因"+ cause);
            // 根据本地消息的状态为失败，有定时任务去处理数据
        }
    }

    /**
     * 消息发送失败返回监控
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("returnedMessage [消息从交换机到队列失败]  message："+message);

    }
}