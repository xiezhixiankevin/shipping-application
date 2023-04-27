package com.xzx.shippingapplication.common.util.rabbit;

/**
 * <Description> ComsumerMessage
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.common.util.rabbit
 */
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xzx.shippingapplication.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.io.IOException;

/**
 * 消费者
 */

@Slf4j
//@Component
public class ComsumerMessage {

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_SHIPPING_ORDER)
    public void handleMessage(Message message,Channel channel) throws  IOException{
        try {
            String json = new String(message.getBody());
            JSONObject jsonObject = JSONObject.parseObject(json);
            log.info("消息了【】handleMessage" +  json);
            //业务处理。
            /**
             * 防止重复消费，可以根据传过来的唯一ID先判断缓存数据中是否有数据
             * 1、有数据则不消费，直接应答处理
             * 2、缓存没有数据，则进行消费处理数据，处理完后手动应答
             * 3、如果消息 处理异常则，可以存入数据库中，手动处理（可以增加短信和邮件提醒功能）
             */



            //手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            log.error("消费消息失败了【】error："+ message.getBody());
            log.error("OrderConsumer  handleMessage {} , error:",message,e);
            // 处理消息失败，将消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }

    }

}