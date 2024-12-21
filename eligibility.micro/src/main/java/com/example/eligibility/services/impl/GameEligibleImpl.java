package com.example.eligibility.services.impl;

import com.example.eligibility.commons.GameCreatedEvent;
import com.example.eligibility.commons.GameEligibleEvent;
import com.example.eligibility.services.GameEligibleService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class GameEligibleImpl implements GameEligibleService {
    @Override
    public Mono<GameEligibleEvent> eligibilityGame(GameCreatedEvent gameCreatedEvent) {
        return Mono.just(gameCreatedEvent)
                .flatMap(this::checkIsEligible)
                .map(givenCreated-> GameEligibleEvent.builder()
                        .id(givenCreated.getId())
                        .name(givenCreated.getName())
                        .userId(givenCreated.getUserId())
                        .isElegible(true)
                        .build()
                );

    }

    private Mono<GameCreatedEvent> checkIsEligible(GameCreatedEvent gameCreatedEvent) {
       return  Mono.just(gameCreatedEvent)
                .filter(given-> !given.getName().isBlank())
                .map(given-> gameCreatedEvent);
    }
}
