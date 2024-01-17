package org.example;

import lombok.Getter;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.MessageFormatter;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class KafkaConsument {
    private List<KafkaConsumer<UUID, String>> kafkaConsumers = new ArrayList<>();
    private final String RENT_TOPIC = "rents";
    int numCunsumers;
    private MessageSaver messageSaver;

    public KafkaConsument(int numConsumers) throws ExecutionException, InterruptedException {
        this.numCunsumers = numConsumers;
        messageSaver = new MessageSaver();
    }

    public void initConsumers() throws ExecutionException, InterruptedException {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "group-rents");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        //consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        for (int i = 0; i < numCunsumers; i++) {
            KafkaConsumer<UUID, String> kafkaConsumer = new KafkaConsumer<>(consumerConfig);
            kafkaConsumer.subscribe(Collections.singleton(RENT_TOPIC));
            kafkaConsumers.add(kafkaConsumer);
            System.out.println("creating consumers");
        }
    }

    public void consume(KafkaConsumer<UUID, String> consumer) {
        boolean saved = false;

        try {
            consumer.poll(0);
            Set<TopicPartition> consumerAssignment = consumer.assignment();
            // System.out.println(consumer.groupMetadata().memberId() + " " + consumerAssignment);
            consumer.seekToBeginning(consumerAssignment);
            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
            MessageFormat formatter = new MessageFormat("Konsument {5}, Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);

                for (ConsumerRecord<UUID, String> record : records) {
                    String result = formatter.format(new Object[]{
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value(),
                            consumer.groupMetadata().memberId()
                    });

                    if (!saved) {
                        messageSaver.saveToMongo(record.value());
                    }
                    saved = true;
                    System.out.println(result);
                }
            }
        } catch (WakeupException we) {
            System.out.println("Job Finished");
        }
    }
    public void consumeTopicByAllConsumers() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(KafkaConsumer<UUID,String>consumer:kafkaConsumers){
             executorService.execute( () -> consume(consumer) );
        }
        /*Thread.sleep(100000);
        for(KafkaConsumer<UUID,String>consumer:kafkaConsumers){
            consumer.wakeup();
        }
        executorService.shutdown(); */
    }

}
