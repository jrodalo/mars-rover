package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public record DoNothing() implements Command {

    public static DoNothing command() {
        return new DoNothing();
    }

    public Rover apply(Rover rover) {
        return rover;
    }

    public CommandType commandType() {
        return CommandType.UNKNOWN;
    }
}
