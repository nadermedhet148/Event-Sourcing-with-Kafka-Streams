# Event Sourcing and Materialized views with Kafka Streams

## Introduction
Kafka helps you to build fast, high through put, fault tolerance, scalable microservices and applications. Kafka Streams stores data in Kafka Clusters (Kafka State Stores) and gets data wicket fast. 

## How to Run?
1. docker compose up
2. Clone this repository and open in IntelliJ or Eclipse as maven project and run `KafkaStreamApplication` class. This will bring up producer class.
3. Go to http://localhost:9021 => Topics=> create topics `customer`, `order`,`customer-order`,`greetings`,`customer-to-ktable-topic` and `order-to-ktable`
4. Go to [EventsListener](src/main/java/com/pj/kafkastream/EventsListener.java) class and execute main method to start REST Proxy (Jetty) which accesses Kafka Materialized views data through REST API
5. Go to [http://localhost:8080/](http://localhost:8080/) to send events and retrieve and see data from Kafka Materialized views
    > 1. EventsSender application and Jetty Server run on different ports on the same machine. I used RestTemplate to get data from Kafka Materialized views and show it in HTML pages

## Technologies Used
1. Kafka Streams with Confluent (https://docs.confluent.io/current/platform.html)
2. Materialized views and Kafka State Stores
3. REST Api using Jetty Server
4. Confluent Schema Registry
5. Avro Serializer/Deserializer
6. Spring Boot
7. Java 8
