package com.ship.order.config;

/**
 * <Description> RabbitConfig
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.config
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 Queue:消息的载体,每个消息都会被投到一个或多个队列。
 Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 Producer:消息生产者,就是投递消息的程序.
 Consumer:消息消费者,就是接受消息的程序.
 Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */
@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    //交换机
    public static final String EXCHANGE_FOR_SHIPPING_ORDER = "exchange_for_shipping_order";

    //队列
    public static final String QUEUE_FOR_SHIPPING_ORDER="queue_for_shipping_order";

    //binding key
    public static final String ROUTINGKEY_FOR_SHIPPING_ORDER = "routingkey_for_shipping_order";

    @Bean
    public ConnectionFactory connectionFactory(){       //初始化
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("testhost");
        connectionFactory.setPublisherConfirms(true); //设置发送消息失败重试
        connectionFactory.setChannelCacheSize(100); //解决多线程发送消息

        return connectionFactory;
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true); //设置发送消息失败重试
        return template;

    }
    //配置使用json转递数据
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无 routingkey的概念
     * HeadersExchange: 通过添加属性key - value匹配
     * DirectExchange: 按照routingkey分发到指定队列
     * TopicExchange : 多关键字匹配
     * @return
     */
    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_FOR_SHIPPING_ORDER,true,false);
    }

    @Bean
    public Queue queueForShippingOrder(){
        return  new Queue(QUEUE_FOR_SHIPPING_ORDER,true);// 队列持久化
    }

    /**
     * 一个交换机可以绑定多个消息队列，也就是消息通过一个交换机,可以分发到不同的队列当中去。
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind( queueForShippingOrder()).to(defaultExchange()).with(ROUTINGKEY_FOR_SHIPPING_ORDER);
    }


}