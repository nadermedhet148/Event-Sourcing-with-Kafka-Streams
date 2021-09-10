package org.djar.member.controller;

import org.djar.Common.model.event.*;
import org.djar.Common.repo.StateStoreRepository;
import org.djar.Common.stream.EventPublisher;
import org.djar.member.domain.Team;
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
    private final StateStoreRepository<Team> teamRepository;

    public CommandController(EventPublisher publisher,
                             StateStoreRepository<Team> boardRepository
                                  ) {
        this.publisher = publisher;
        this.teamRepository = boardRepository;
    }

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> CreateTeam(@RequestBody CreateTeamRequest request) {
        Event event = new TeamCreated(request.getTeamId(), request.getName());
        logger.debug("TeamCreated: {}", event);
        return publisher.fire(event);
    }

    @PostMapping("/boards/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> CreateCard(@RequestBody AddMemberToTeam request) {
        Event event = new MemberAddedToTeam(request.getTeamId() , request.getMemberId(),request.getName());
        logger.debug("MemberAddedToTeam: {}", event);
        return publisher.fire(event);
    }



}
