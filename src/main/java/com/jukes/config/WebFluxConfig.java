package com.jukes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
@ComponentScan("com.jukes")
@Slf4j
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public WebClient restTemplate() {
        ExchangeFilterFunction filter = (clientRequest, nextFilter) -> {
            log.info(
                    "WebClient executed url: {} with {} method!",
                    clientRequest.url(),
                    clientRequest.method()
            );
            return nextFilter.exchange(clientRequest);
        };
        return WebClient.builder()
                .filter(filter)
                .build();
    }
}