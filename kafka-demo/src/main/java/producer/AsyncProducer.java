package producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @ClassName: CustomProducer
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/2 5:37 下午
 */
public class AsyncProducer {
    public static void main(String[] args) {
        // 1. 创建 kafka 生产者的配置对象
        Properties properties = new Properties();

        // 2. 给 kafka 配置对象添加配置信息：bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "172.16.8.46:9092, 172.16.8.46:9093, 172.16.8.46:9094");

        // 3、key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        // 4. 创建 kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        // 5. 调用 send 方法,发送消息
        try {
            for (int i = 0; i <5 ; i++) {
                producer.send(new ProducerRecord<String, String>("TestTopic", "macay111" + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6. 关闭资源
        producer.close();
    }
}
