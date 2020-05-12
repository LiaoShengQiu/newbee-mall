package com.example.newbeemall.controller.mall.workMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: windy
 * @create: 2019-08-03 10:02
 */
@Controller
public class WorkProducer {

    private Logger logger= LoggerFactory.getLogger(WorkProducer.class);


    @Autowired
    private AmqpTemplate rabbitTemplate;
int  num = 0;
    @RequestMapping("/sendMessage2")
    @ResponseBody
    public void send(Object obj) throws IOException, TimeoutException {

        ++num;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 创建一个新的连接
        Connection connection = connectionFactory.newConnection();
// 创建一个新的频道
        Channel channel = connection.createChannel();
        /*for (int i=0;i<50;i++){
            this.rabbitTemplate.convertAndSend("workMq", i);
        }*/
        this.rabbitTemplate.convertAndSend("workMq", obj);

        //1 限流方式
        channel.basicQos(0,10,true);
    }
}
