package dev.matthiesen.cobblemon_move_tutor.common.registry;

import dev.matthiesen.cobblemon_move_tutor.common.commands.MoveTutorCMD;
import dev.matthiesen.matthiesen_core.common.registry.AbstractCommandRegistry;

public final class CommandRegistry extends AbstractCommandRegistry {
    private static final CommandRegistry INSTANCE = new CommandRegistry();

    public CommandRegistry() {
        super();
    }

    public static void init() {}

    static {
        INSTANCE.register(MoveTutorCMD.INSTANCE);
    }
}
