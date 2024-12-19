package com.example.eligibility.configuration;

import com.example.eligibility.commons.GameCreatedEvent;
import com.example.eligibility.commons.GameEligibleEvent;
import com.example.eligibility.processors.EligibilityGameProcessors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class StreamConfig {
    @Bean
    public Function <Flux<GameCreatedEvent>, Flux<GameEligibleEvent>> gameCreatedBinding(final EligibilityGameProcessors processor) {

        return processor::process;
    }
}
