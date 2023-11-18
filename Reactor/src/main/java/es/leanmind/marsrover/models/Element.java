package es.leanmind.marsrover.models;

public record Element(String id, Position position, Direction direction, Type type) {

    public enum Type {
        ROVER,
        ROCK
    }
}
