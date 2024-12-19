package com.example.eligibility.services;

import com.example.eligibility.commons.GameCreatedEvent;
import com.example.eligibility.commons.GameEligibleEvent;
import reactor.core.publisher.Mono;

public interface GameEligibleService {
    Mono<GameEligibleEvent> eligibilityGame(GameCreatedEvent gameCreatedEvent);
}
