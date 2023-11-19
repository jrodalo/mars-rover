package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public record MoveForward() implements Command {

    public static MoveForward command() {
        return new MoveForward();
    }

    public Rover apply(Rover rover) {
        return rover.moveForward();
    }

    public CommandType commandType() {
        return CommandType.MOVE_FORWARD;
    }
}
