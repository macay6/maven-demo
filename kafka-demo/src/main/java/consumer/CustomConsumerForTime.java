package consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * @ClassName: CustomConsumerByHandSync
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/9 6:53 下午
 */
public class CustomConsumerForTime {
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

        HashMap<TopicPartition, Long> timestampToSearch = new
                HashMap<>();
        // 封装集合存储，每个分区对应一天前的数据
        for (TopicPartition topicPartition : assignment) {
            timestampToSearch.put(topicPartition,
                    System.currentTimeMillis() - 1 * 24 * 3600 * 1000);
        }

        // 获取从 1 天前开始消费的每个分区的 offset
        Map<TopicPartition, OffsetAndTimestamp> offsets =
                consumer.offsetsForTimes(timestampToSearch);

        // 遍历所有分区，对每个分区设置消费时间。
        for (TopicPartition topicPartition: assignment) {
            OffsetAndTimestamp offsetAndTimestamp =
                    offsets.get(topicPartition);
       // 根据时间指定开始消费的位置
            if (offsetAndTimestamp != null){
                consumer.seek(topicPartition,
                        offsetAndTimestamp.offset());
            }
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
