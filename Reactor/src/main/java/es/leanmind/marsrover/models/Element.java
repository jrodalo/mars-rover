package es.leanmind.marsrover.models;

import java.util.UUID;

public record Element(UUID id, Position position, Direction direction, Type type) {

    public enum Type {
        ROVER,
        ROCK
    }
}
