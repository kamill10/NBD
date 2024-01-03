package p.lodz;

import lombok.Getter;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.MessageFormatter;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Getter
public class KafkaConsument {
    private List<KafkaConsumer<UUID, String>> kafkaConsumers = new ArrayList<>();
    private final String RENT_TOPIC = "rents";
    int numCunsumers;

    public KafkaConsument(int numConsumers) throws ExecutionException, InterruptedException {
        this.numCunsumers = numConsumers;
    }

    public void initConsumers() throws ExecutionException, InterruptedException {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "group_rents");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        //consumerConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //consumerConfig.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "10000");
        for (int i = 0; i < numCunsumers; i++) {
            KafkaConsumer<UUID, String> kafkaConsumer = new KafkaConsumer<>(consumerConfig);
            kafkaConsumer.subscribe(List.of(RENT_TOPIC));
            kafkaConsumers.add(kafkaConsumer);
        }
    }

    public void consume() {
        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
        for (Consumer<UUID, String> kafkaConsumer : kafkaConsumers) {
            ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<UUID,String> record : records){
                String result = formatter.format(new Object[]{record.topic(), record. partition(), record.offset(), record.key(), record.value()});
           System.out.println(result);
            }
            kafkaConsumer.commitAsync();
        }
    }

}
