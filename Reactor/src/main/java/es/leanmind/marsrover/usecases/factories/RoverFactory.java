package es.leanmind.marsrover.usecases.factories;

import es.leanmind.marsrover.AppConfiguration;
import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.models.Speed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoverFactory {
    private final AppConfiguration appConfiguration;
    private final RandomFactory randomFactory;

    public Rover newRoverWith(String id) {
        var position = getRandomPositionWithin(appConfiguration.width(), appConfiguration.height());
        var direction = getRandomDirection();
        var speed = getRandomSpeed();

        return Rover.create(id, position, direction, speed);
    }

    private Position getRandomPositionWithin(int lowerLimit, int upperLimit) {
        return Position.of(randomFactory.generateNumber(0, lowerLimit), randomFactory.generateNumber(0, upperLimit));
    }

    private Direction getRandomDirection() {
        return Direction.of(randomFactory.generateNumber(0, 360));
    }

    private Speed getRandomSpeed() {
        return Speed.of(randomFactory.generateNumber(5, 15));
    }
}
