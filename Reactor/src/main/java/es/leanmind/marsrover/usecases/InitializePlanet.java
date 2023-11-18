package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.AppConfiguration;
import es.leanmind.marsrover.repositories.RockRepository;
import es.leanmind.marsrover.usecases.factories.RockFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class InitializePlanet {

    private final AppConfiguration appConfiguration;
    private final RockFactory rockFactory;
    private final RockRepository rockRepository;

    public Mono<Void> execute() {
        return rangeFrom(appConfiguration.initialNumberOfRocks())
            .map(index -> rockFactory.newRockAtRandomPosition())
            .flatMap(rockRepository::save)
            .then();
    }

    private static Flux<Integer> rangeFrom(int endExclusive) {
        return Flux.fromStream(IntStream.range(0, endExclusive).boxed());
    }
}
