package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MoveBackwardTest {

    private static final Direction EAST = Direction.of(0);
    private static final Direction NORTH_EAST = Direction.of(45);
    private static final Direction NORTH = Direction.of(90);

    @Test
    void decrements_latitude_when_moving_backward_horizontally() {
        var rover = Rover.create(UUID.randomUUID(), Position.of(0, 0), EAST, Speed.of(5));

        var actual = MoveBackward.command().apply(rover);

        assertThat(actual).isEqualTo(rover.atPosition(Position.of(-5, 0)));
    }

    @Test
    void decrements_longitude_when_moving_backward_vertically() {
        var rover = Rover.create(UUID.randomUUID(), Position.of(0, 0), NORTH, Speed.of(5));

        var actual = MoveBackward.command().apply(rover);

        assertThat(actual).isEqualTo(rover.atPosition(Position.of(0, -5)));
    }

    @Test
    void decrements_latitude_and_longitude_when_moving_backward_diagonally() {
        var rover = Rover.create(UUID.randomUUID(), Position.of(0, 0), NORTH_EAST, Speed.of(5));

        var actual = MoveBackward.command().apply(rover);

        assertThat(actual).isEqualTo(rover.atPosition(Position.of(-4, -4)));
    }
}
