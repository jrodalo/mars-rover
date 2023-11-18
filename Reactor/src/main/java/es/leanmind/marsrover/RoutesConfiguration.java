package es.leanmind.marsrover;

import es.leanmind.marsrover.handlers.AppHttpHandler;
import es.leanmind.marsrover.handlers.AppWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouterFunction<ServerResponse> httpRoutes(AppHttpHandler appHttpHandler) {
        return route(GET("/"), appHttpHandler::showIndex);
    }

    @Bean
    public HandlerMapping webSocketRoutes(AppWebSocketHandler appWebSocketHandler) {
        var urlMapping = Map.of(
            "/api/websocket", appWebSocketHandler
        );

        return new SimpleUrlHandlerMapping(urlMapping, Ordered.HIGHEST_PRECEDENCE);
    }
}
