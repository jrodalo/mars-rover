package es.leanmind.marsrover.usecases.factories;

import es.leanmind.marsrover.AppConfiguration;
import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RockFactory {
    private final AppConfiguration appConfiguration;
    private final RandomFactory randomFactory;

    public Rock newRockAtRandomPosition() {
        var id = getRandomId();
        var position = getRandomPositionWithin(appConfiguration.width(), appConfiguration.height());
        var direction = getRandomDirection();

        return Rock.create(id, position, direction);
    }

    private String getRandomId() {
        return randomFactory.generateId();
    }

    private Position getRandomPositionWithin(int lowerLimit, int upperLimit) {
        return Position.of(randomFactory.generateNumber(0, lowerLimit), randomFactory.generateNumber(0, upperLimit));
    }

    private Direction getRandomDirection() {
        return Direction.of(randomFactory.generateNumber(0, 360));
    }
}
