package es.leanmind.marsrover.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record Planet(Collection<Rover> rovers, Collection<Rock> rocks) {

    public static Planet of(Collection<Rover> rovers, Collection<Rock> rocks) {
        return new Planet(rovers, rocks);
    }

    public List<Element> elements() {
        return Stream.concat(
            rovers.stream().map(Rover::toElement),
            rocks.stream().map(Rock::toElement)
        ).toList();
    }
}
