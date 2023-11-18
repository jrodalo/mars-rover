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
            rovers.stream().map(this::toElement),
            rocks.stream().map(this::toElement)
        ).toList();
    }

    private Element toElement(Rover rover) {
        return new Element(rover.id(), rover.position(), rover.direction(), Element.Type.ROVER);
    }

    private Element toElement(Rock rock) {
        return new Element(rock.id(), rock.position(), rock.direction(), Element.Type.ROCK);
    }
}
