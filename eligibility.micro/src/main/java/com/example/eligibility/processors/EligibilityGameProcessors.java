package com.example.eligibility.processors;

import com.example.eligibility.commons.GameCreatedEvent;
import com.example.eligibility.commons.GameEligibleEvent;
import com.example.eligibility.services.GameEligibleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class EligibilityGameProcessors {
    private final GameEligibleService gameEligebleService;

    public EligibilityGameProcessors(GameEligibleService gameEligebleService) {
        this.gameEligebleService = gameEligebleService;
    }

    public Flux<GameEligibleEvent> process(Flux<GameCreatedEvent> gameCreatedEventFlux){
        return gameCreatedEventFlux.doOnNext(given-> log.info("Entry event : {}"))
                .flatMap(gameEligebleService::eligibilityGame)
                .onErrorContinue(this:: handleError);

    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error processing event: {}", object, throwable);
    }
}
