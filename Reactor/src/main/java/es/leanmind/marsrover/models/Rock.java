package es.leanmind.marsrover.models;

public record Rock(String id, Position position, Direction direction) {

    public static Rock create(String id, Position position, Direction direction) {
        return new Rock(id, position, direction);
    }
}
