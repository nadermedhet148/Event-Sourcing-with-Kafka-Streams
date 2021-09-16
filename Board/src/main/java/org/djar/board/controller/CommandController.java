package org.djar.board.controller;


import org.djar.Common.model.event.BoardCreated;
import org.djar.Common.model.event.CardCreated;
import org.djar.Common.model.event.CardStatusChanged;
import org.djar.Common.model.event.Event;
import org.djar.Common.repo.StateStoreRepository;
import org.djar.Common.stream.EventPublisher;
import org.djar.board.Domain.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/command", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommandController {

    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

    private final EventPublisher publisher;
    private final StateStoreRepository<Board> boardRepository;

    public CommandController(EventPublisher publisher,
                                  StateStoreRepository<Board> boardRepository
                                  ) {
        this.publisher = publisher;
        this.boardRepository = boardRepository;
    }

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> CreateBoard(@RequestBody CreateBoardRequest request) {
        Event event = new BoardCreated(request.getBoardId(), request.getName());
        logger.debug("BoardCreated: {}", event);
        return publisher.fire(event);
    }

    @PostMapping("/boards/cards")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> CreateCard(@RequestBody CardRequest request) {
        CardCreated.Type type = null ;

        if(request.getType() == "note")
            type = CardCreated.Type.NOTE;
        if(request.getType() == "task")
            type = CardCreated.Type.TASK;

        Event event = new CardCreated(request.getCardId(),
                request.getDescription(),
                request.getBoardId(),
                request.getMemberId(),
                type);
        logger.debug("BoardCreated: {}", event);
        return publisher.fire(event);
    }

    @PutMapping("/boards/cards/{card}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> UpdateCardStatus(@RequestBody ChangeCardStatusRequest request) {
        CardStatusChanged.Status status = null ;

        if(request.getStatus() == "in_progress")
            status = CardStatusChanged.Status.IN_PROCESS;
        if(request.getStatus() == "completed")
            status = CardStatusChanged.Status.COMPLETED;

        Event event = new CardStatusChanged(request.getCardId() , request.getBoardId(),status);
        logger.debug("CardStatusChanged: {}", event);
        return publisher.fire(event);
    }


}
