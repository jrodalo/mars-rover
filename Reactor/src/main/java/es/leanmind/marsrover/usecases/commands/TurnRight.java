package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public class TurnRight implements Command {

    public static TurnRight command() {
        return new TurnRight();
    }

    public Rover apply(Rover rover) {
        return rover.turnRight();
    }

    public CommandType commandType() {
        return CommandType.TURN_RIGHT;
    }
}
