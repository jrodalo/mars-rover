package es.leanmind.marsrover.usecases;

import es.leanmind.marsrover.models.Rover;
import es.leanmind.marsrover.repositories.RoverRepository;
import es.leanmind.marsrover.usecases.commands.*;
import es.leanmind.marsrover.usecases.factories.RoverFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApplyActionToRover {

    private final RoverRepository roverRepository;
    private final RoverFactory roverFactory;

    private final Commands commands = Commands.of(
        Start.command(),
        MoveForward.command(),
        MoveBackward.command(),
        TurnLeft.command(),
        TurnRight.command()
    );

    public Mono<Rover> execute(Action action) {
        return getOrCreateRoverFrom(action)
            .map(rover -> apply(action, rover))
            .flatMap(this::save);
    }

    private Mono<Rover> getOrCreateRoverFrom(Action action) {
        return roverRepository
            .findById(action.elementId())
            .switchIfEmpty(Mono.defer(() -> Mono.just(roverFactory.newRoverWith(action.elementId()))));
    }

    private Rover apply(Action action, Rover rover) {
        return commands.get(action.command()).apply(rover);
    }

    private Mono<Rover> save(Rover updatedRover) {
        return roverRepository.save(updatedRover);
    }
}
