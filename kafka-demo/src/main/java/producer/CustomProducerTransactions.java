package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @ClassName: CustomProducerTransactions
 * @Description:
 * @Author: Macay
 * @Date: 2022/7/3 3:14 下午
 */
public class CustomProducerTransactions {
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

        // 设置事务 id（必须），事务 id 任意起名
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,
                "transaction_id_0");

        // 4. 创建 kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // 初始化事务
        producer.initTransactions();
        // 开启事务
        producer.beginTransaction();


        // 5. 调用 send 方法,发送消息
        try {
            for (int i = 0; i <5 ; i++) {
                producer.send(new ProducerRecord<String, String>("TestTopic", "transaction_id_0" + i));
            }
            int i = 1 / 0;
            // 提交事务
            producer.commitTransaction();
        } catch (Exception e) {
            // 终止事务
            producer.abortTransaction();
        } finally {
            // 6. 关闭资源
            producer.close();
        }
    }
}
