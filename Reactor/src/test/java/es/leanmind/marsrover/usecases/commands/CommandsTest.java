package es.leanmind.marsrover.usecases.commands;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandsTest {

    private static final Commands COMMANDS = Commands.of(
        Start.command(),
        MoveForward.command()
    );

    @Test
    void allows_to_get_commands_by_command_type() {
        assertThat(COMMANDS.get(CommandType.MOVE_FORWARD)).isEqualTo(MoveForward.command());
    }

    @Test
    void does_nothing_when_the_requested_command_does_not_exist() {
        assertThat(COMMANDS.get(CommandType.UNKNOWN)).isEqualTo(DoNothing.command());
    }

    @Test
    void does_not_allow_to_add_two_commands_with_the_same_command_type() {
        assertThatThrownBy(() -> Commands.of(
            Start.command(),
            Start.command()
        ))
            .isInstanceOf(Commands.InvalidConfiguration.class)
            .hasMessage("There are two commands with the same type: START");
    }
}
