package es.leanmind.marsrover.models;

import java.util.UUID;

public record Rock(UUID id, Position position, Direction direction) {

    public static Rock create(UUID id, Position position, Direction direction) {
        return new Rock(id, position, direction);
    }

    public Element toElement() {
        return new Element(id, position, direction, Element.Type.ROCK);
    }
}
