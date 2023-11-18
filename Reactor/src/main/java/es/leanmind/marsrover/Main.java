package es.leanmind.marsrover;

import es.leanmind.marsrover.usecases.InitializePlanet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Component
    @RequiredArgsConstructor
    public static class EventListenerExampleBean {

        private final InitializePlanet initializePlanet;

        @EventListener
        public void onApplicationEvent(ContextRefreshedEvent ignore) {
            initializePlanet.execute().block();
        }
    }
}