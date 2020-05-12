package com.example.newbeemall.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * work模式
 */
@Configuration
public class RabbitConfig {
    private final static String workQunne = "workMq";

    @Bean
    public Queue workMq() {
        return new Queue(workQunne);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                               ConnectionFactory connectionFactory)  {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(1);
        configurer.configure(factory, connectionFactory);
        //   factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);             //开启手动 ack
        return factory;
    }

}
