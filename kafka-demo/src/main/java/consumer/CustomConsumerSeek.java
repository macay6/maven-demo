package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @ClassName: CustomConsumerByHandSync
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/9 6:53 下午
 */
public class CustomConsumerSeek {
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
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test5");
        // 是否自动提交 offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                false);
        // 3. 创建 kafka 消费者
        KafkaConsumer<String, String> consumer = new
                KafkaConsumer<>(properties);
        // 4. 设置消费主题 形参是列表
        consumer.subscribe(Arrays.asList("TestTopic"));

        Set<TopicPartition> assignment= new HashSet<>();

        // 保证分区分配方案已经制定完毕
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofSeconds(1));
        // 获取消费者分区分配信息（有了分区分配信息才能开始消费）
            assignment = consumer.assignment();
        }
        // 遍历所有分区，并指定 offset 从 100 的位置开始消费
        for (TopicPartition tp: assignment) {
            consumer.seek(tp, 100);
        }

        // 5. 消费数据
        while (true) {
        // 读取消息
            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(1));
        // 输出消息
            for (ConsumerRecord<String, String> consumerRecord :
                    consumerRecords) {
                System.out.println(consumerRecord);
            }
        // 异步提交 offset
            consumer.commitAsync();
        }
    }
}
