package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.models.*;
import es.leanmind.marsrover.repositories.RockRepository;
import es.leanmind.marsrover.repositories.RoverRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPlanetTest {

    private static final Rock FIRST_ROCK = Rock.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(90));
    private static final Rock SECOND_ROCK = Rock.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(90));
    private static final Rover FIRST_ROVER = Rover.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(90), Speed.of(5));
    private static final Rover SECOND_ROVER = Rover.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(90), Speed.of(5));

    @Test
    void returns_all_the_existing_elements_in_the_planet() {
        var roverRepository = fakeRoverRepository();
        var rockRepository = fakeRockRepository();
        var getPlanet = new GetPlanet(roverRepository, rockRepository);
        var expected = Planet.of(
            List.of(
                FIRST_ROVER,
                SECOND_ROVER
            ),
            List.of(
                FIRST_ROCK,
                SECOND_ROCK
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
            FIRST_ROVER,
            SECOND_ROVER
        )));
        return roverRepository;
    }

    private RockRepository fakeRockRepository() {
        var rockRepository = mock(RockRepository.class);
        when(rockRepository.findAll()).thenReturn(Mono.just(List.of(
            FIRST_ROCK,
            SECOND_ROCK
        )));
        return rockRepository;
    }
}
