package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.models.Planet;
import es.leanmind.marsrover.models.Rock;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.repositories.RockRepository;
import es.leanmind.marsrover.repositories.RoverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GetPlanet {

    private final RoverRepository roverRepository;
    private final RockRepository rockRepository;

    public Mono<Planet> execute() {
        return Mono.zip(
            roverRepository.findAll(),
            rockRepository.findAll(),
            GetPlanet::from
        );
    }

    private static Planet from(Collection<Rover> rovers, Collection<Rock> rocks) {
        return Planet.of(rovers, rocks);
    }
}
