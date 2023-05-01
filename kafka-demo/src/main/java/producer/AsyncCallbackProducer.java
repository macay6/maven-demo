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
public class AsyncCallbackProducer {
    public static void main(String[] args) {
        // 1. 创建 kafka 生产者的配置对象
        Properties properties = new Properties();

        // 2. 给 kafka 配置对象添加配置信息：bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.3.9:9092, 192.168.3.9:9093, 192.168.3.9:9094");

        // 3、key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        //  添加自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "partition.MyPartition");

        // 4. 创建 kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        // 5. 调用 send 方法,发送消息
        try {
            for (int i = 0; i <5 ; i++) {
                // 依次指定 key 值为 a,b,f ，数据 key 的 hash 值与 2 个分区求余，分别发往 1、0
                producer.send(new ProducerRecord<String, String>("TestTopic", "hello", "macay999" + i), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                        if (exception == null) {
                            System.out.println("主题：" + recordMetadata.topic() + ",分区：" + recordMetadata.partition() );
                        } else {
                            exception.printStackTrace();
                        }
                    }
                });
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6. 关闭资源
        producer.close();
    }
}
