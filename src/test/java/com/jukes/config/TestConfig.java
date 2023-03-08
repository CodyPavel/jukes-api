package com.jukes.config;

import com.jukes.controller.JukesController;
import com.jukes.remote.RemoteJukeService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(JukesController.class)
public class TestConfig {
    @Autowired
    public WebTestClient webTestClient;

    @MockBean
    public RemoteJukeService remoteJukeService;

}
