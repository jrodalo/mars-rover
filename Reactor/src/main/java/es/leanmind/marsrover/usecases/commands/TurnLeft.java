package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public record TurnLeft() implements Command {

    public static TurnLeft command() {
        return new TurnLeft();
    }

    public Rover apply(Rover rover) {
        return rover.turnLeft();
    }

    public CommandType commandType() {
        return CommandType.TURN_LEFT;
    }
}
