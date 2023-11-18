package es.leanmind.marsrover.models;

public record Speed(int value) {

    public static Speed of(int value) {
        return new Speed(value);
    }
}
