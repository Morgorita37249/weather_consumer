package org.example;
import java.util.HashMap;
import java.util.Map;

public class WeatherStats {
    private final Map<String, Integer> sunnyDays = new HashMap<>();
    private final Map<String, Integer> rainyDays = new HashMap<>();
    private final Map<String, Integer> maxTemperatures = new HashMap<>();

    public void updateStats(Weather weather) {
        String city = weather.city();

        if (weather.condition().equals("солнечно")) {
            sunnyDays.put(city, sunnyDays.getOrDefault(city, 0) + 1);
        }

        if (weather.condition().equals("дождь")) {
            rainyDays.put(city, rainyDays.getOrDefault(city, 0) + 1);
        }

        maxTemperatures.put(city, Math.max(
                maxTemperatures.getOrDefault(city, Integer.MIN_VALUE),
                weather.temperature()
        ));
    }

    public void printStats() {
        System.out.println("\n=== Статистика ===");
        sunnyDays.forEach((city, count) -> System.out.println(city + ": солнечных дней — " + count));
        rainyDays.forEach((city, count) -> System.out.println(city + ": дождливых дней — " + count));
        maxTemperatures.forEach((city, temp) -> System.out.println(city + ": максимальная температура — " + temp + "°C"));
    }
}