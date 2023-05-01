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
public class CustomProducerNoPartAndKey {
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

        // batch.size：批次大小，默认 16K
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        // linger.ms：等待时间，默认 0
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 5);

        // RecordAccumulator：缓冲区大小，默认 32M：buffer.memory
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // compression.type：压缩，默认 none，可配置值 gzip、snappy、lz4 和 zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");

        // 4. 创建 kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        // 5. 调用 send 方法,发送消息
        try {
            for (int i = 0; i <200 ; i++) {
                producer.send(new ProducerRecord<String, String>("TestTopic", "duoduo" + i), new Callback() {
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
