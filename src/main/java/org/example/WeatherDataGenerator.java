package org.example;
import java.util.Random;

public class WeatherDataGenerator {
    private static final Random random = new Random();
    private static final String[] CITIES = {"Москва", "Санкт-Петербург", "Магадан", "Курган", "Тюмень"};
    private static final String[] CONDITIONS = {"солнечно", "облачно", "дождь"};

    public static Weather generateRandomWeather() {
        String city = CITIES[random.nextInt(CITIES.length)];
        String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];
        int temperature = random.nextInt(36);
        return new Weather(city, condition, temperature);
    }
}

record Weather(String city, String condition, int temperature) {}

