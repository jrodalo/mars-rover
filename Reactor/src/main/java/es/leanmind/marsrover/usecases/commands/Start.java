package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public class Start implements Command {

    public static Start command() {
        return new Start();
    }

    public Rover apply(Rover rover) {
        return rover;
    }

    public CommandType commandType() {
        return CommandType.START;
    }
}
