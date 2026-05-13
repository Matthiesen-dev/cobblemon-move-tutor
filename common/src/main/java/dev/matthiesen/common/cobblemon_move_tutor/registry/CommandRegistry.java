package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.commands.MoveTutorCMD;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
import dev.matthiesen.common.matthiesen_lib.command.AbstractCommand;

public class CommandRegistry {
    public static void init() {}

    static {
        register(new MoveTutorCMD());
    }

    public static void register(AbstractCommand command) {
        MatthiesenLib.registerCommand(command);
    }
}
