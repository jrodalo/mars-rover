package es.leanmind.marsrover.handlers;

import es.leanmind.marsrover.models.Element;
import es.leanmind.marsrover.models.Planet;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.usecases.ApplyActionToRover;
import es.leanmind.marsrover.usecases.GetPlanet;
import es.leanmind.marsrover.usecases.commands.Action;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
public class AppWebSocketHandler implements WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(AppWebSocketHandler.class);
    private final Sinks.Many<Element> updatesChannel = Sinks.many().multicast().directBestEffort();
    private final GetPlanet getPlanet;
    private final ApplyActionToRover applyActionToRover;

    @NonNull
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        return onNewConnection(session).then();
    }

    private Flux<Object> onNewConnection(WebSocketSession session) {
        return Flux.merge(
            sendCurrentPlanetStatus(session),
            listenForNewActions(session),
            sendUpdates(session)
        );
    }

    private Mono<Void> sendCurrentPlanetStatus(WebSocketSession session) {
        return session.send(
            getPlanet.execute()
                .flatMapIterable(Planet::elements)
                .map(this::toJson)
                .map(session::textMessage)
        );
    }

    private Flux<Element> listenForNewActions(WebSocketSession session) {
        return session.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .doOnNext(this::logIncomingMessage)
            .map(this::toAction)
            .flatMap(applyActionToRover::execute)
            .map(Rover::toElement)
            .doOnNext(updatesChannel::tryEmitNext);
    }

    private Mono<Void> sendUpdates(WebSocketSession session) {
        return session.send(
            updatesChannel.asFlux()
                .map(this::toJson)
                .map(session::textMessage)
        );
    }

    private void logIncomingMessage(String webSocketMessage) {
        logger.info("Received message: {}", webSocketMessage);
    }

    private Action toAction(String json) {
        return Json.read(json, Action.class);
    }

    private String toJson(Object object) {
        return Json.from(object);
    }
}
