package dev.matthiesen.common.cobblemon_move_tutor.registry;

import com.mojang.brigadier.CommandDispatcher;
import dev.matthiesen.common.cobblemon_move_tutor.commands.MoveTutorCMD;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.ICommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.List;

public class CommandRegistry {
    public static void init(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection context) {
        new MoveTutorCMD().register(dispatcher, registry, context);
    }
}
