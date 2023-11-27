package es.leanmind.marsrover.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Element;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.usecases.commands.Action;
import es.leanmind.marsrover.usecases.commands.CommandType;
import es.leanmind.marsrover.usecases.factories.RandomFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppWebSocketHandlerTest {

    private static final int A_FIXED_NUMBER = 10;
    private final WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
    @LocalServerPort
    private String port;

    @TestConfiguration
    public static class FakeConfiguration {
        @Bean
        public RandomFactory randomFactory() {
            var randomFactory = mock(RandomFactory.class);
            when(randomFactory.generateNumber(anyInt(), anyInt())).thenReturn(A_FIXED_NUMBER);
            when(randomFactory.generateId()).thenCallRealMethod();
            return randomFactory;
        }
    }

    @Test
    void sends_existing_elements_to_the_client_when_connecting_for_the_first_time() throws Exception {
        var messages = new Messages();
        var firstRoverId = UUID.randomUUID();
        var startFirstRover = actionAsJson(CommandType.START, firstRoverId);
        var secondRoverId = UUID.randomUUID();
        var startSecondRover = actionAsJson(CommandType.START, secondRoverId);

        webSocket(session -> session.send(Mono.just(startFirstRover).map(session::textMessage))).block();
        webSocket(session -> session.send(Mono.just(startSecondRover).map(session::textMessage))).block();

        Thread.sleep(500); // We have to give the repository some time to save the elements

        webSocket(session -> session.receive()
            .take(12)
            .map(WebSocketMessage::getPayloadAsText)
            .doOnNext(messages::add)
            .then())
            .block();

        assertThat(messages.get()).contains(aRoverWith(firstRoverId), aRoverWith(secondRoverId));
    }

    private Mono<Void> webSocket(WebSocketHandler webSocketHandler) throws URISyntaxException {
        var uri = new URI("ws://localhost:%s/api/websocket".formatted(port));
        return webSocketClient.execute(uri, webSocketHandler);
    }

    private String actionAsJson(CommandType commandType, UUID elementId) throws JsonProcessingException {
        return Json.from(Action.of(commandType, elementId));
    }

    private static Element aRoverWith(UUID id) {
        return new Element(
            id,
            Position.of(A_FIXED_NUMBER, A_FIXED_NUMBER),
            Direction.of(A_FIXED_NUMBER),
            Element.Type.ROVER
        );
    }

    private static class Messages {
        private final AtomicReferenceArray<String> actualReference = new AtomicReferenceArray<>(12);
        private final AtomicInteger index = new AtomicInteger(0);

        public void add(String element) {
            actualReference.set(index.getAndIncrement(), element);
        }

        public List<Element> get() {
            return IntStream.range(0, actualReference.length())
                .mapToObj(actualReference::get)
                .map(a -> Json.read(a, Element.class))
                .toList();
        }
    }
}
