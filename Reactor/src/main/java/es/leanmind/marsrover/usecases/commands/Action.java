package es.leanmind.marsrover.usecases.commands;

import java.util.UUID;

public record Action(CommandType command, UUID elementId) {

    public static Action of(CommandType commandType, UUID elementId) {
        return new Action(commandType, elementId);
    }
}
