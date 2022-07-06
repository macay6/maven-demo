package producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @ClassName: CustomProducer
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/2 5:37 下午
 */
public class SyncProducer {
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


        // 5. 同步发送.get()
        try {
            for (int i = 0; i <5 ; i++) {
                producer.send(new ProducerRecord<String, String>("TestTopic", "macay22" + i)).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6. 关闭资源
        producer.close();
    }
}
