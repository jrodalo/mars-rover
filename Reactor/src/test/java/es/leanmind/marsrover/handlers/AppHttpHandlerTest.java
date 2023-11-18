package es.leanmind.marsrover.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppHttpHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void renders_the_main_page() {
        webClient.get()
            .uri("/")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(MediaType.TEXT_HTML);
    }
}
