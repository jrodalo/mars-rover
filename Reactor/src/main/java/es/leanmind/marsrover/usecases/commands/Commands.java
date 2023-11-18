package es.leanmind.marsrover.usecases.commands;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Commands(Map<CommandType, Command> commands) {
    private static final DoNothing DO_NOTHING = DoNothing.command();

    public static Commands of(Command... commands) {
        var commandMap = Stream.of(commands).collect(toEnumMap());

        return new Commands(commandMap);
    }

    public Command get(CommandType commandType) {
        return commands.getOrDefault(commandType, DO_NOTHING);
    }

    private static Collector<Command, ?, EnumMap<CommandType, Command>> toEnumMap() {
        return Collectors.toMap(
            Command::commandType,
            Function.identity(),
            Commands::onDuplicatedKey,
            () -> new EnumMap<>(CommandType.class)
        );
    }

    private static Command onDuplicatedKey(Command left, Command right) {
        throw InvalidConfiguration.because("There are two commands with the same type: " + left.commandType());
    }

    protected static class InvalidConfiguration extends RuntimeException {
        public InvalidConfiguration(String message) {
            super(message);
        }

        public static InvalidConfiguration because(String reason) {
            return new InvalidConfiguration(reason);
        }
    }
}
