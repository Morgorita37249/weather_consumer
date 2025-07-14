# Weather Kafka Producer & Consumer

Проект реализует систему сбора и анализа данных о погоде с использованием Apache Kafka. Включает продюсера, генерирующего случайные погодные данные, и консьюмера, который собирает статистику.
Weather Producer генерирует случайные данные о погоде для разных городов

Отправляет сообщения в Kafka-топик weather-topic

Автоматически создает топик при первом запуске

Формат сообщений: Город: {city}, Погода: {condition}, Температура: {temp}°C

Weather Consumer

Подписывается на топик weather-topic

Собирает статистику:

Количество солнечных дней по городам

Количество дождливых дней по городам

Максимальные температуры по городам

Выводит статистику в консоль

# Технологии
Java 17

Apache Kafka 3.6

Maven для сборки

# Установка и запуск

Установите Kafka:

# Запуск Zookeeper

bin/zookeeper-server-start.sh config/zookeeper.properties

# Запуск Kafka

bin/kafka-server-start.sh config/server.properties

Сборка проекта:

mvn clean package

Запуск приложений:


# Запуск consumer

java -jar target/weather-consumer.jar

# Запуск producer

java -jar target/weather-producer.jar
