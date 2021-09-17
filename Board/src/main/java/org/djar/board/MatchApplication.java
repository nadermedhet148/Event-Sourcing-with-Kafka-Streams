package org.djar.board;

import java.util.Properties;

import org.djar.Common.model.event.Event;
import org.djar.Common.repo.StateStoreRepository;
import org.djar.Common.stream.EventPublisher;
import org.djar.Common.stream.JsonPojoSerde;
import org.djar.Common.stream.KafkaStreamsStarter;
import org.djar.Common.util.MicroserviceUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.djar.board.domain.Board;
import org.djar.board.snapshot.DomainUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MatchApplication {

    private static final Logger logger = LoggerFactory.getLogger(MatchApplication.class);

    private static final String APP_ID = MicroserviceUtils.applicationId(MatchApplication.class);

    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrapAddress;

    @Value("${apiVersion}")
    private int apiVersion;

    @Value("${kafkaTimeout:60000}")
    private long kafkaTimeout;

    @Value("${streamsStartupTimeout:20000}")
    private long streamsStartupTimeout;

    @Bean
    public KafkaStreams kafkaStreams() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        DomainUpdater snapshotBuilder = new DomainUpdater();
        Topology topology = streamsBuilder.build();
        snapshotBuilder.init(topology);
        KafkaStreamsStarter starter = new KafkaStreamsStarter(kafkaBootstrapAddress, topology, APP_ID);
        starter.setKafkaTimeout(kafkaTimeout);
        starter.setStreamsStartupTimeout(streamsStartupTimeout);
        return starter.start();
    }

    @Bean
    public EventPublisher eventPublisher() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPojoSerde.class.getName());
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, APP_ID);
        KafkaProducer kafkaProducer = new KafkaProducer<String, Event>(producerProps);
        return new EventPublisher(kafkaProducer, APP_ID, apiVersion);
    }


    @Bean
    public StateStoreRepository<Board> boardRepository() {
        return new StateStoreRepository<>(kafkaStreams(), DomainUpdater.Board_STORE);
    }


    public static void main(String[] args) {
        logger.info("Application ID: {}", APP_ID);
        SpringApplication.run(MatchApplication.class, args);
    }
}
