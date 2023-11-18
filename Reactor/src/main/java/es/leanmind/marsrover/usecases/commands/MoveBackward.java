package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public class MoveBackward implements Command {

    public static MoveBackward command() {
        return new MoveBackward();
    }

    public Rover apply(Rover rover) {
        return rover.moveBackward();
    }

    public CommandType commandType() {
        return CommandType.MOVE_BACKWARD;
    }
}
