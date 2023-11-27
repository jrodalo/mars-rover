package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import es.leanmind.marsrover.repositories.RoverRepository;
import es.leanmind.marsrover.usecases.commands.Action;
import es.leanmind.marsrover.usecases.commands.CommandType;
import es.leanmind.marsrover.usecases.factories.RoverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApplyActionToRoverTest {

    private static final Rover EXISTING_ROVER = Rover.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(90), Speed.of(10));

    @ParameterizedTest
    @CsvSource(value = {
        // COMMAND,       EXPECTED_LATITUDE,   EXPECTED_LONGITUDE
        "MOVE_FORWARD,    0,                   10",
        "MOVE_BACKWARD,   0,                   -10",
    })
    void applies_move_actions_to_existing_rovers(CommandType commandType, int expectedLatitude, int expectedLongitude) {
        var roverRepository = fakeRoverRepository();
        var fakeRoverFactory = fakeRoverFactory();
        var applyActionToRover = new ApplyActionToRover(roverRepository, fakeRoverFactory);
        var expectedRover = EXISTING_ROVER.atPosition(Position.of(expectedLatitude, expectedLongitude));

        applyActionToRover.execute(Action.of(commandType, EXISTING_ROVER.id()))
            .as(StepVerifier::create)
            .assertNext(actualRover -> assertEquals(expectedRover, actualRover))
            .verifyComplete();

        verify(roverRepository).save(expectedRover);
    }

    @ParameterizedTest
    @CsvSource(value = {
        // COMMAND,       EXPECTED_ANGLE
        "TURN_LEFT,       80",
        "TURN_RIGHT,      100",
    })
    void applies_rotation_actions_to_existing_rovers(CommandType commandType, int expectedAngle) {
        var roverRepository = fakeRoverRepository();
        var fakeRoverFactory = fakeRoverFactory();
        var applyActionToRover = new ApplyActionToRover(roverRepository, fakeRoverFactory);
        var expectedRover = EXISTING_ROVER.atDirection(Direction.of(expectedAngle));

        applyActionToRover.execute(Action.of(commandType, EXISTING_ROVER.id()))
            .as(StepVerifier::create)
            .assertNext(actualRover -> assertEquals(expectedRover, actualRover))
            .verifyComplete();

        verify(roverRepository).save(expectedRover);
    }

    @Test
    void applies_start_action_to_new_rovers() {
        var roverRepository = fakeRoverRepository();
        var fakeRoverFactory = fakeRoverFactory();
        var applyActionToRover = new ApplyActionToRover(roverRepository, fakeRoverFactory);
        var newRoverId = UUID.randomUUID();
        var expectedRover = Rover.create(newRoverId, Position.of(0, 0), Direction.of(90), Speed.of(10));

        applyActionToRover.execute(Action.of(CommandType.START, newRoverId))
            .as(StepVerifier::create)
            .assertNext(actualRover -> assertEquals(expectedRover, actualRover))
            .verifyComplete();

        verify(roverRepository).save(expectedRover);
    }

    @Test
    void ignores_unknown_actions() {
        var roverRepository = fakeRoverRepository();
        var fakeRoverFactory = fakeRoverFactory();
        var applyActionToRover = new ApplyActionToRover(roverRepository, fakeRoverFactory);

        applyActionToRover.execute(Action.of(CommandType.UNKNOWN, EXISTING_ROVER.id()))
            .as(StepVerifier::create)
            .expectNext(EXISTING_ROVER)
            .verifyComplete();
    }

    private RoverRepository fakeRoverRepository() {
        var rockRepository = mock(RoverRepository.class);
        when(rockRepository.save(any(Rover.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(rockRepository.findById(EXISTING_ROVER.id())).thenReturn(Mono.just(EXISTING_ROVER));
        when(rockRepository.findById(any(UUID.class))).thenReturn(Mono.empty());
        return rockRepository;
    }

    private RoverFactory fakeRoverFactory() {
        var roverFactory = mock(RoverFactory.class);
        when(roverFactory.newRoverWith(any(UUID.class)))
            .thenAnswer(invocation -> Rover.create(invocation.getArgument(0), Position.of(0, 0), Direction.of(90), Speed.of(10)));
        return roverFactory;
    }
}
