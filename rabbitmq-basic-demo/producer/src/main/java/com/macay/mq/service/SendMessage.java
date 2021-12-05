package com.macay.mq.service;

import com.macay.mq.utils.MqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
public class SendMessage {
    private static final Logger logger = LoggerFactory.getLogger(SendMessage.class);

    public static void main(String[] args) throws IOException, TimeoutException {

        String message = "hello world";
        // 获取连接
        Connection connection = MqConnectionUtil.getConnection(); // 类似于JDBC操作的数据库连接
        // 创建channel
        Channel channel = connection.createChannel();// 类似于JDBC操作的statement

        //定义队列(使用Java代码在MQ中新建一个队列)
        //参数1：定义的队列名称
        //参数2：队列中的数据是否持久化（如果选择了持久化）
        //参数3: 是否排外（当前队列是否为当前连接私有）
        //参数4：自动删除（当此队列的连接数为0时，此队列会销毁（无论队列中是否还有数据））
        //参数5：设置当前队列的参数
        channel.queueDeclare("queue8",false,false,false,null);

        // 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
        // 参数2：目标队列名称
        // 参数3：设置当前这条消息的属性（如设置过期时间 10）
        // 参数4：消息的内容
        channel.basicPublish("","queue8",null,message.getBytes());
        // logger.warn("已发送消息：" + message  );
        System.out.println("已发送消息：" + message);

        // 关闭连接
        channel.close();
        connection.close();

    }
}
