package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: CustomConsumerByHandSync
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/9 6:53 下午
 */
public class CustomConsumerByHandAsync {
    public static void main(String[] args) {
        // 1. 创建 kafka 消费者配置类
        Properties properties = new Properties();
        // 2. 添加配置参数
        // 添加连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.3.9:9092, 192.168.3.9:9093, 192.168.3.9:9094");
        // 配置序列化 必须
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        // 配置消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test4");
        // 是否自动提交 offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                false);
        // 3. 创建 kafka 消费者
        KafkaConsumer<String, String> consumer = new
                KafkaConsumer<>(properties);
        // 4. 设置消费主题 形参是列表
        consumer.subscribe(Arrays.asList("TestTopic"));
        // 5. 消费数据
        while (true) {
        // 读取消息
            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(1));
        // 输出消息
            for (ConsumerRecord<String, String> consumerRecord :
                    consumerRecords) {
                System.out.println(consumerRecord.value());
            }
        // 异步提交 offset
            consumer.commitAsync();
        }
    }
}
