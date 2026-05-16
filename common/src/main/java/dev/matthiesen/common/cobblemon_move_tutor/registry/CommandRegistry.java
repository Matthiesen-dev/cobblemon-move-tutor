package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.commands.MoveTutorCMD;
import dev.matthiesen.common.matthiesen_lib.registry.AbstractCommandRegistry;

public class CommandRegistry extends AbstractCommandRegistry {
    static {
        register(new MoveTutorCMD());
    }
}
