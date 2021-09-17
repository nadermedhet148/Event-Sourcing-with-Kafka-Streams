package org.djar.board.snapshot;



import org.djar.Common.model.event.BoardCreated;
import org.djar.Common.model.event.CardCreated;
import org.djar.Common.model.event.CardStatusChanged;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.KeyValueStore;
import org.djar.board.domain.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.djar.board.domain.Board;

import java.util.Objects;

import static org.djar.Common.stream.StreamsUtils.addProcessor;
import static org.djar.Common.stream.StreamsUtils.addStore;

public class DomainUpdater {

    private static final Logger logger = LoggerFactory.getLogger(DomainUpdater.class);

    public static final String Board_STORE = "board_store";


    public void init(Topology topology) {
        addProcessor(topology, CardCreated.class, (eventId, event, store) -> {
            Board board = findBoard(store , event.getBoardId());
            Card.Type type = Card.Type.NOTE;
            if(event.getType() == CardCreated.Type.TASK)
                type = Card.Type.TASK;

            board.newCard(event.getCardId(),event.getMemberId(),type, event.getDescription());
            store.put(board.getBoardId(), board);

        }, Board_STORE);

        addProcessor(topology, BoardCreated.class, (eventId, event, store) -> {
           Board board = new Board();
           board.setBoardId(event.getBoardId());
           board.setName(event.getName());
           store.put(board.getBoardId(), board);
        }, Board_STORE);

        addProcessor(topology, CardStatusChanged.class, (eventId, event, store) -> {
            Board board = findBoard(store , event.getBoardId());
            Card.Type type = Card.Type.NOTE;
            if(event.getStatus() == CardStatusChanged.Status.IN_PROCESS)
                board.workOnCard(event.getCardId());
            if(event.getStatus() == CardStatusChanged.Status.COMPLETED)
                board.completeCard(event.getCardId());
            store.put(board.getBoardId(), board);

        }, Board_STORE);





        addStore(topology, Board.class, Board_STORE, new Class[] { CardCreated.class , BoardCreated.class , CardStatusChanged.class});
    }

    private Board findBoard(KeyValueStore<String, Object> store, String boardId) {
        return (Board) Objects.requireNonNull(store.get(boardId), "board not found: " + boardId);
    }
}
