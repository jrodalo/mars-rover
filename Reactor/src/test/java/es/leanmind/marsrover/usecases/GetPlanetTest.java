package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.models.*;
import es.leanmind.marsrover.repositories.RockRepository;
import es.leanmind.marsrover.repositories.RoverRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPlanetTest {

    @Test
    void returns_all_the_existing_elements_in_the_planet() {
        var roverRepository = fakeRoverRepository();
        var rockRepository = fakeRockRepository();
        var getPlanet = new GetPlanet(roverRepository, rockRepository);
        var expected = Planet.of(
            List.of(
                Rover.create("1", Position.of(0, 0), Direction.of(90), Speed.of(5)),
                Rover.create("2", Position.of(0, 0), Direction.of(90), Speed.of(5))
            ),
            List.of(
                Rock.create("3", Position.of(0, 0), Direction.of(90)),
                Rock.create("4", Position.of(0, 0), Direction.of(90))
            )
        );

        getPlanet.execute()
            .as(StepVerifier::create)
            .assertNext(actual -> assertEquals(expected, actual))
            .verifyComplete();
    }

    private RoverRepository fakeRoverRepository() {
        var roverRepository = mock(RoverRepository.class);
        when(roverRepository.findAll()).thenReturn(Mono.just(List.of(
            Rover.create("1", Position.of(0, 0), Direction.of(90), Speed.of(5)),
            Rover.create("2", Position.of(0, 0), Direction.of(90), Speed.of(5))
        )));
        return roverRepository;
    }

    private RockRepository fakeRockRepository() {
        var rockRepository = mock(RockRepository.class);
        when(rockRepository.findAll()).thenReturn(Mono.just(List.of(
            Rock.create("3", Position.of(0, 0), Direction.of(90)),
            Rock.create("4", Position.of(0, 0), Direction.of(90))
        )));
        return rockRepository;
    }
}
