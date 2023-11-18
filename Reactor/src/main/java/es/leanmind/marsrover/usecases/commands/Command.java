package es.leanmind.marsrover.usecases.commands;

import es.leanmind.marsrover.models.Rover;

public interface Command {
    CommandType commandType();

    Rover apply(Rover rover);
}
