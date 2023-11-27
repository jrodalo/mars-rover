package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TurnLeftTest {

    @Test
    void decrements_angle_when_turning_left() {
        var rover = Rover.create(UUID.randomUUID(), Position.of(0, 0), Direction.of(0), Speed.of(5));

        var actual = TurnLeft.command().apply(rover);

        assertThat(actual).isEqualTo(rover.atDirection(Direction.of(-5)));
    }
}
