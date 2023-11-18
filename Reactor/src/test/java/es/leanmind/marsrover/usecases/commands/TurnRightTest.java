package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnRightTest {

    @Test
    void increments_angle_when_turning_right() {
        var rover = Rover.create("1", Position.of(0, 0), Direction.of(0), Speed.of(5));

        var actual = TurnRight.command().apply(rover);

        assertThat(actual).isEqualTo(rover.atDirection(Direction.of(5)));
    }
}