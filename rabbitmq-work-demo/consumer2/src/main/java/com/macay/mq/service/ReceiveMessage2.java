package com.macay.mq.service;

import com.macay.mq.utils.MqConnectionUtil;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: SendMessage
 * @Description:
 * @Author: Macay
 * @Date: 2021/12/5 10:50 上午
 */
public class ReceiveMessage2 {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveMessage2.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MqConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body就是从队列中获取的数据
                String msg = new String(body);
                System.out.println("Consumer2接收："+msg);
            }
        };

        channel.basicConsume("queue3",true,consumer);
    }
}
