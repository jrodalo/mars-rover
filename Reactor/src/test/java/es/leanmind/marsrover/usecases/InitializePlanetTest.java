package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.AppConfiguration;
import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rock;
import es.leanmind.marsrover.repositories.RockRepository;
import es.leanmind.marsrover.usecases.factories.RockFactory;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitializePlanetTest {

    @Test
    void initializes_the_game_with_random_elements() {
        var appConfiguration = AppConfiguration.builder().initialNumberOfRocks(10).build();
        var rockFactory = fakeRockFactory();
        var rockRepository = fakeRockRepository();
        var initializePlanet = new InitializePlanet(appConfiguration, rockFactory, rockRepository);

        initializePlanet.execute()
            .as(StepVerifier::create)
            .verifyComplete();

        verify(rockRepository, times(10)).save(any(Rock.class));
    }

    private RockRepository fakeRockRepository() {
        var rockRepository = mock(RockRepository.class);
        when(rockRepository.save(any(Rock.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        return rockRepository;
    }

    private RockFactory fakeRockFactory() {
        var rockFactory = mock(RockFactory.class);
        when(rockFactory.newRockAtRandomPosition()).thenReturn(Rock.create("1", Position.of(0, 0), Direction.of(90)));
        return rockFactory;
    }
}
