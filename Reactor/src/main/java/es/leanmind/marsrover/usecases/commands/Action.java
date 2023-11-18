package es.leanmind.marsrover.usecases.commands;

public record Action(CommandType command, String elementId) {

    public static Action of(CommandType commandType, String elementId) {
        return new Action(commandType, elementId);
    }
}