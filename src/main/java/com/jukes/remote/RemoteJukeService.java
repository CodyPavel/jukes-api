package com.jukes.remote;

import com.jukes.model.jukes.Juke;
import com.jukes.model.settings.Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 *   RemoteJukeService
 *   loads public API data
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteJukeService {

    private final static String JUKE_URL = "/touchtunes/tech-assignment/jukes";
    private final static String SETTINGS_URL = "/touchtunes/tech-assignment/settings";

    @Value("${public.server.baseUrl}")
    String baseUrl;

    private final WebClient webClient;

    /**
     * WebClient GET call to remote REST services to get jukes
     *
     * @return Flux<Juke> jukes
     */
    public Flux<Juke> loadJukes() {
        return webClient.get()
                .uri(baseUrl + JUKE_URL)
                .retrieve()
                .bodyToFlux(Juke.class);
    }

    /**
     * WebClient GET call to remote REST services to get settings of jukes
     *
     * @return Mono<Settings> settings
     */
    public Mono<Settings> loadSettings() {
        return webClient.get()
                .uri(baseUrl + SETTINGS_URL)
                .retrieve()
                .bodyToMono(Settings.class);
    }
}
