package org.djar.football.view.basic;

import static org.apache.kafka.common.serialization.Serdes.String;
import org.apache.kafka.common.serialization.Serdes;
import static org.djar.Common.stream.StreamsUtils.materialized;

import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.djar.Common.model.event.CardCreated;
import org.djar.Common.model.view.CardSummary;
import org.djar.Common.util.Topics;
import org.djar.Common.stream.JsonPojoSerde;

/**
 * Builder that creates Kafka Streams topology for creating card summary.
 *
 */
public class BoardSummariesBuilder {

    private static final String Card_Created_TOPIC = Topics.eventTopicName(CardCreated.class);


    private final JsonPojoSerde<CardCreated> cardCreatedSerde = new JsonPojoSerde<>(CardCreated.class);



    private final JsonPojoSerde<CardSummary> cardSummarySerde = new JsonPojoSerde<>(CardSummary.class);

    private final StreamsBuilder builder;

    public BoardSummariesBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void build() {
        KStream<String, CardCreated> cardCreatedStream = builder
                .stream(Card_Created_TOPIC, Consumed.with(String(), cardCreatedSerde));

        KTable<String, CardSummary> CardSummaryTable = cardCreatedStream
                .groupByKey(Serialized.with(Serdes.String(), cardCreatedSerde))
                .aggregate(() -> new CardSummary(),
                        (id, createdCard, cardSummary) -> cardSummary.aggregate(createdCard),
                        materialized(Card_Created_TOPIC, cardSummarySerde));

        CardSummaryTable.toStream().to(Card_Created_TOPIC, Produced.with(String(), cardSummarySerde));
    }
}
