package com.jukes.controller;

import com.jukes.extension.TestExtension;
import com.jukes.model.ApiError;
import com.jukes.model.jukes.Juke;
import com.jukes.model.settings.Settings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.mockito.Mockito.when;

class JukesControllerTest extends TestExtension {

    @Value("${test.baseUrl}")
    private String baseUrl;

    @Test
    public void testGetAllJukes() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "f600ede7-0709-4ca0-b95d-95a5315b9385")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(30));
    }

    @Test
    public void testGetJukesWithWrongIdError() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "wrongId")
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList(ApiError.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getMessage())
                                .isEqualTo("Setings not found by IdwrongId"))
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getStatus())
                                .isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetWithError() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBodyList(ApiError.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull()).consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getMessage())
                                .isEqualTo("400 BAD_REQUEST \"Required query parameter 'settingId' is not present.\""))
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getStatus())
                                .isEqualTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetFilteringBySetting() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "3a6423cf-f226-4cb1-bf51-2954bc0941d1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(1))
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getModel())
                                .isEqualTo("angelina"))
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).get(0).getId())
                                .isEqualTo("5ca94a8acc046e7aa8040605"))
                .consumeWith(response -> Assertions.assertThat(Objects.requireNonNull(
                        response.getResponseBody()).get(0).getComponents().get(0).getName()).isEqualTo("money_receiver"))
                .consumeWith(response -> Assertions.assertThat(Objects.requireNonNull(
                        response.getResponseBody()).get(0).getComponents().get(1).getName()).isEqualTo("led_matrix"))
                .consumeWith(response -> Assertions.assertThat(Objects.requireNonNull(
                        response.getResponseBody()).get(0).getComponents().get(2).getName()).isEqualTo("speaker"))
                .consumeWith(response -> Assertions.assertThat(Objects.requireNonNull(
                        response.getResponseBody()).get(0).getComponents().get(3).getName()).isEqualTo("touchscreen"))
                .consumeWith(response -> Assertions.assertThat(Objects.requireNonNull(
                        response.getResponseBody()).get(0).getComponents().get(4).getName()).isEqualTo("money_pcb"));
    }

    @Test
    public void testGetAllFilteredByModel() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "f600ede7-0709-4ca0-b95d-95a5315b9385")
                        .queryParam("model", "fusion")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(10));
    }

    @Test
    public void testGetAllFilteredByWrongModel() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "f600ede7-0709-4ca0-b95d-95a5315b9385")
                        .queryParam("model", "WrongModel")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(0));
    }

    @Test
    public void testGetAllFilteredByModelAndLimited() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "f600ede7-0709-4ca0-b95d-95a5315b9385")
                        .queryParam("model", "fusion")
                        .queryParam("limit", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(1));
    }

    @Test
    public void testGetAllFilteredByModelWithOffset() {
        Flux<Juke> jukeFlux = getJukes();
        Mono<Settings> settingsMono = getSettings();

        when(remoteJukeService.loadJukes()).thenReturn(jukeFlux);
        when(remoteJukeService.loadSettings()).thenReturn(settingsMono);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl)
                        .queryParam("settingId", "f600ede7-0709-4ca0-b95d-95a5315b9385")
                        .queryParam("model", "fusion")
                        .queryParam("offset", 1)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Juke.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull())
                .consumeWith(response ->
                        Assertions.assertThat(Objects.requireNonNull(response.getResponseBody()).size())
                                .isEqualTo(9));
    }
}