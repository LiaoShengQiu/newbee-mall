package com.example.newbeemall.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitMQConfig {
    public static  Binding noargs;

    //库存交换机
    public static final String STORY_EXCHANGE = "STORY_EXCHANGE";
    //用户交换机
    public static final String USER_EXCHANGE = "USER_EXCHANGE";

    //订单交换机
    public static final String ORDER_EXCHANGE = "ORDER_EXCHANGE";

    //库存队列
    public static final String STORY_QUEUE = "STORY_QUEUE";

    //用户队列
    public static final String USER_QUEUE = "USER_NAMES";
    //订单队列
    public static final String ORDER_QUEUE = "ORDER_QUEUE";


    //库存路由键
    public static final String STORY_ROUTING_KEY = "STORY_ROUTING_KEY";
    //用户
    public static final String USER_EN_KEY = "USER_EN_KEY";

    //订单路由键
    public static final String ORDER_ROUTING_KEY = "ORDER_ROUTING_KEY";
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    //创建库存交换机
    @Bean
    public Exchange getStoryExchange() {
        return ExchangeBuilder.directExchange(STORY_EXCHANGE).durable(true).build();
    }
    //创建库存队列
    @Bean
    public Queue getStoryQueue() {
        return new Queue(STORY_QUEUE);
    }
//用户队列
    @Bean
    public Queue getUserQueue() {
        return new Queue(USER_QUEUE);
    }

  /*  @Resource
    private RabbitAdmin rabbitAdmin;*/
    //库存交换机和库存队列绑定
    @Bean
    public Binding bindStory() {
        noargs = BindingBuilder.bind(getStoryQueue()).to(getStoryExchange()).with(STORY_ROUTING_KEY).noargs();
        return noargs;
    }

    //解绑
    /*public void unBindStory() throws IOException {
        Binding noargs = BindingBuilder.bind(getStoryQueue()).to(getStoryExchange()).with(STORY_ROUTING_KEY).noargs();
        rabbitAdmin.removeBinding(noargs);
    }*/

//    @Bean
//    public Binding bindUser() {
//        return BindingBuilder.bind(getStoryQueue()).to(getStoryExchange()).with(USER_EN_KEY).noargs();
//    }


    //创建订单队列
    @Bean
    public Queue getOrderQueue() {
        return new Queue(ORDER_QUEUE);
    }
    //创建订单交换机
    @Bean
    public Exchange getOrderExchange() {
        return ExchangeBuilder.directExchange(ORDER_EXCHANGE).durable(true).build();
    }
    //订单队列与订单交换机进行绑定
    @Bean
    public Binding bindOrder() {
        return BindingBuilder.bind(getOrderQueue()).to(getOrderExchange()).with(ORDER_ROUTING_KEY).noargs();
    }
}