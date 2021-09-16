package org.djar.ui;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.djar.Common.model.view.CardSummary;
import org.djar.Common.stream.JsonPojoSerde;
import org.djar.Common.stream.StreamsUtils;
import org.djar.Common.util.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class StatisticsKeeper {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsKeeper.class);

    public static final String Card_Created_STORE = "card_created_store";

    private final StreamsBuilder streamsBuilder;
    private final SimpMessagingTemplate stomp;
    private final Executor executor = Executors.newCachedThreadPool();

    public StatisticsKeeper(StreamsBuilder streamsBuilder, SimpMessagingTemplate stomp) {
        this.streamsBuilder = streamsBuilder;
        this.stomp = stomp;
    }

    public void build() {
        updateStoreAndDashboard(CardSummary.class, Card_Created_STORE);
    }

    private <T> void updateStoreAndDashboard(Class<T> viewType, String store) {
        JsonPojoSerde serde = new JsonPojoSerde<>(viewType);
        streamsBuilder.stream(Topics.viewTopicName(viewType), Consumed.with(Serdes.String(), serde))
                .peek(this::updateDashboard)
                .groupByKey()
                .reduce((aggValue, newValue) -> newValue, StreamsUtils.materialized(store, serde));
    }

    private void updateDashboard(Object key, Object value) {
        // emit WebSocket notification
        executor.execute(() -> {
            logger.debug("Update dashboard {}: {}->{}", value.getClass().getSimpleName(), key, value);
            stomp.convertAndSend("/topic/" + value.getClass().getSimpleName(), value);
        });
    }
}
