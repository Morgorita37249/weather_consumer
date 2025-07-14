package org.example;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class WeatherProducer {
    private static final String TOPIC = "weather-topic";

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        createTopic(props);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        while (true) {
            Weather weather = WeatherDataGenerator.generateRandomWeather();
            String message = String.format(
                    "Город: %s, Погода: %s, Температура: %d°C",
                    weather.city(), weather.condition(), weather.temperature()
            );

            producer.send(new ProducerRecord<>(TOPIC, weather.city(), message));
            System.out.println("Отправлено: " + message);

            TimeUnit.SECONDS.sleep(2);
        }
    }

    private static void createTopic(Properties props) {
        try (AdminClient admin = AdminClient.create(props)) {
            if (!admin.listTopics().names().get().contains(TOPIC)) {

                NewTopic newTopic = new NewTopic(TOPIC, 1, (short) 1);

                CreateTopicsResult result = admin.createTopics(
                        Collections.singleton(newTopic)
                );

                result.all().get();
                System.out.println("Топик " + TOPIC + " создан");
            }
        } catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                System.out.println("Топик уже существует");
            } else {
                e.printStackTrace();
            }
        }
    }
}