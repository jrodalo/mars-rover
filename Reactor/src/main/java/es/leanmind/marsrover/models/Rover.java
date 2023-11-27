package es.leanmind.marsrover.models;

import java.util.UUID;

public record Rover(UUID id, Position position, Direction direction, Speed speed) {

    public static Rover create(UUID id, Position position, Direction direction, Speed speed) {
        return new Rover(id, position, direction, speed);
    }

    public Rover atPosition(Position position) {
        return Rover.create(id, position, direction, speed);
    }

    public Rover atDirection(Direction direction) {
        return Rover.create(id, position, direction, speed);
    }

    public Rover moveForward() {
        var newPosition = position.moveForwardAt(direction, speed);
        return Rover.create(id, newPosition, direction, speed);
    }

    public Rover moveBackward() {
        var newPosition = position.moveBackwardAt(direction, speed);
        return Rover.create(id, newPosition, direction, speed);
    }

    public Rover turnLeft() {
        var newDirection = direction.turnLeftAt(speed);
        return Rover.create(id, position, newDirection, speed);
    }

    public Rover turnRight() {
        var newDirection = direction.turnRightAt(speed);
        return Rover.create(id, position, newDirection, speed);
    }

    public Element toElement() {
        return new Element(id, position, direction, Element.Type.ROVER);
    }
}
