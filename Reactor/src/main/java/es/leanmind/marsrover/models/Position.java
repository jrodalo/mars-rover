package es.leanmind.marsrover.models;

public record Position(int latitude, int longitude) {

    public static Position of(int latitude, int longitude) {
        return new Position(latitude, longitude);
    }

    public Position moveForwardAt(Direction direction, Speed speed) {
        var newLatitude = (int) Math.round(latitude + direction.horizontalDisplacement() * speed.value());
        var newLongitude = (int) Math.round(longitude + direction.verticalDisplacement() * speed.value());
        return Position.of(newLatitude, newLongitude);
    }

    public Position moveBackwardAt(Direction direction, Speed speed) {
        var newLatitude = (int) Math.round(latitude - direction.horizontalDisplacement() * speed.value());
        var newLongitude = (int) Math.round(longitude - direction.verticalDisplacement() * speed.value());
        return Position.of(newLatitude, newLongitude);
    }
}
