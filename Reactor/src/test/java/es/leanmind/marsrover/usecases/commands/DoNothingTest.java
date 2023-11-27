package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DoNothingTest {

    @Test
    void does_nothing() {
        var rover = Rover.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(45), Speed.of(5));

        var actual = DoNothing.command().apply(rover);

        assertThat(actual).isEqualTo(rover);
    }
}
