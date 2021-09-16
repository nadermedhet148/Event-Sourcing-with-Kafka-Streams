package org.djar.ui.controller;

import org.djar.Common.model.view.CardSummary;
import org.djar.Common.repo.StateStoreRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/ui", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    private final StateStoreRepository<CardSummary> matchScoreRepo;


    public StatisticsController(StateStoreRepository<CardSummary> matchScoreRepo) {
        this.matchScoreRepo = matchScoreRepo;

    }

    @GetMapping("/cardSummary")
    public Flux<CardSummary> getCardSummary() {
        return matchScoreRepo.findAll();
    }


}
