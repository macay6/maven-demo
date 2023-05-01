package com.macay.mq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: MqConnectionUtil
 * @Description:
 * @Author: Macay
 * @Date: 2021/12/5 10:25 上午
 */
public class MqConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2.在工厂对象中设置MQ的连接信息(ip,port,virtualhost,username,password)
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("host1");
        factory.setUsername("macay");
        factory.setPassword("admin123");
        //3.通过工厂对象获取与MQ的链接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MqConnectionUtil.getConnection();
        System.out.println(connection);
    }
}
