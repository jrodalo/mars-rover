package es.leanmind.marsrover.repositories;

import es.leanmind.marsrover.models.Rock;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RockRepository {
    private final Map<UUID, Rock> database = new ConcurrentHashMap<>();

    public Mono<Rock> save(Rock rock) {
        return Mono.just(rock)
            .doOnNext(this::addRock)
            .thenReturn(rock);
    }

    public Mono<Collection<Rock>> findAll() {
        return Mono.just(database.values());
    }

    private void addRock(Rock rock) {
        this.database.put(rock.id(), rock);
    }
}
