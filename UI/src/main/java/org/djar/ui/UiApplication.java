package org.djar.ui;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.djar.Common.model.view.CardSummary;
import org.djar.Common.repo.StateStoreRepository;
import org.djar.Common.stream.KafkaStreamsStarter;
import org.djar.Common.util.MicroserviceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class UiApplication {

    private static final Logger logger = LoggerFactory.getLogger(UiApplication.class);

    private static final String APP_ID = MicroserviceUtils.applicationId(UiApplication.class);

    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrapAddress;

    @Value("${kafkaTimeout:60000}")
    private long kafkaTimeout;

    @Value("${streamsStartupTimeout:20000}")
    private long streamsStartupTimeout;

    @Autowired
    private SimpMessagingTemplate stomp;


    @Bean
    public KafkaStreams kafkaStreams() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        StatisticsKeeper statisticsBuilder = new StatisticsKeeper(streamsBuilder, stomp);
        statisticsBuilder.build();
        Topology topology = streamsBuilder.build();
        KafkaStreamsStarter starter = new KafkaStreamsStarter(kafkaBootstrapAddress, topology, APP_ID);
        starter.setKafkaTimeout(kafkaTimeout);
        starter.setStreamsStartupTimeout(streamsStartupTimeout);
        return starter.start();
    }

    @Bean
    public StateStoreRepository<CardSummary> matchScoresRepo() {
        return new StateStoreRepository<>(kafkaStreams(), StatisticsKeeper.Card_Created_STORE);
    }


    public static void main(String[] args) {
        logger.info("Application ID: {}", APP_ID);
        SpringApplication.run(UiApplication.class, args);
    }
}
