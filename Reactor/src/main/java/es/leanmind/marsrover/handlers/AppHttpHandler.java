package es.leanmind.marsrover.handlers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AppHttpHandler {

    public Mono<ServerResponse> showIndex(ServerRequest ignore) {
        return ServerResponse.ok().body(BodyInserters.fromResource(new ClassPathResource("/public/index.html")));
    }
}
