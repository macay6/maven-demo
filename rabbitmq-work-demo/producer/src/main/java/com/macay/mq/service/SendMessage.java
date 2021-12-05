package com.macay.mq.service;

import com.macay.mq.utils.MqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: SendMessage
 * @Description:
 * @Author: Macay
 * @Date: 2021/12/5 10:50 上午
 */
public class SendMessage {
    private static final Logger logger = LoggerFactory.getLogger(SendMessage.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("需要发送的消息：");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String msg = scanner.nextLine();
            // 获取连接
            Connection connection = MqConnectionUtil.getConnection(); // 类似于JDBC操作的数据库连接
            // 创建channel
            Channel channel = connection.createChannel();// 类似于JDBC操作的statement

            channel.basicPublish("","queue3",null,msg.getBytes());
            // logger.warn("已发送消息：" + message);
            System.out.println("已发送消息：" + msg);

            // 关闭连接
            channel.close();
            connection.close();
        }

    }
}
