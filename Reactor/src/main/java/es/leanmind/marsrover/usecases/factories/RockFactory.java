package es.leanmind.marsrover.usecases.factories;

import es.leanmind.marsrover.AppConfiguration;
import es.leanmind.marsrover.models.Direction;
import es.leanmind.marsrover.models.Position;
import es.leanmind.marsrover.models.Rock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    private UUID getRandomId() {
        return randomFactory.generateId();
    }

    private Position getRandomPositionWithin(int width, int height) {
        var margin = 50;
        var latitude = randomFactory.generateNumber(0, width - margin);
        var longitude = randomFactory.generateNumber(0, height - margin);
        return Position.of(latitude, longitude);
    }

    private Direction getRandomDirection() {
        return Direction.of(randomFactory.generateNumber(0, 360));
    }
}
