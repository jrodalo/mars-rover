package es.leanmind.marsrover.repositories;

import es.leanmind.marsrover.models.Rover;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoverRepository {
    private final Map<UUID, Rover> database = new ConcurrentHashMap<>();

    public Mono<Rover> save(Rover rover) {
        return Mono.just(rover)
            .doOnNext(this::addRover)
            .thenReturn(rover);
    }

    public Mono<Rover> findById(UUID id) {
        return Mono.justOrEmpty(database.get(id));
    }

    public Mono<Collection<Rover>> findAll() {
        return Mono.just(database.values());
    }

    private void addRover(Rover rover) {
        this.database.put(rover.id(), rover);
    }
}
