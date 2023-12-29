package p.lodz;

import lombok.Getter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Getter
public class KafkaConsument {
    private List<Consumer<UUID, String>> kafkaConsumers = new ArrayList<>();
    private final String RENT_TOPIC = "rents";

    public KafkaConsument(int numConsumers) throws ExecutionException, InterruptedException {
        initConsumers(numConsumers);
    }

    private void initConsumers(int numConsumers) throws ExecutionException, InterruptedException {
        for (int i = 0; i < numConsumers; i++) {
            Properties consumerConfig = new Properties();
            consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
            consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "group_rents");
            consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");

            Consumer<UUID, String> kafkaConsumer = new KafkaConsumer<>(consumerConfig);
            kafkaConsumer.subscribe(List.of(RENT_TOPIC));

            kafkaConsumers.add(kafkaConsumer);
        }
    }

    public void consume() {
        for (Consumer<UUID, String> kafkaConsumer : kafkaConsumers) {
            ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            kafkaConsumer.commitAsync();
        }
    }
}
