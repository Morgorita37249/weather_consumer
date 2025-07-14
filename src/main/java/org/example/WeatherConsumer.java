package org.example;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class WeatherConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "weather-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("weather-topic"));

        WeatherStats stats = new WeatherStats();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                System.out.println("Получено: " + record.value());
                stats.updateStats(parseWeather(record.value()));
            });
            stats.printStats();
        }
    }

    private static Weather parseWeather(String message) {

        String[] parts = message.split(", ");
        String city = parts[0].split(": ")[1];
        String condition = parts[1].split(": ")[1];
        int temperature = Integer.parseInt(parts[2].split(": ")[1].replace("°C", ""));
        return new Weather(city, condition, temperature);
    }
}
