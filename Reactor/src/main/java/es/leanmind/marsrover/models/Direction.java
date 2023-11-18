package es.leanmind.marsrover.models;

public record Direction(int angle) {

    public static Direction of(int angle) {
        return new Direction(angle);
    }

    public Direction turnLeftAt(Speed speed) {
        return Direction.of(angle - speed.value());
    }

    public Direction turnRightAt(Speed speed) {
        return Direction.of(angle + speed.value());
    }

    public double horizontalDisplacement() {
        return Math.cos(angleAsRadians());
    }

    public double verticalDisplacement() {
        return Math.sin(angleAsRadians());
    }

    private double angleAsRadians() {
        return angle * Math.PI / 180;
    }
}
