package dev.matthiesen.cobblemon_move_tutor.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.registry.PermissionRegistry;
import dev.matthiesen.cobblemon_move_tutor.common.util.TutorMenuProvider;
import dev.matthiesen.matthiesen_core.common.api.command.CoreCommand;
import dev.matthiesen.matthiesen_core.common.utility.commands.CommandBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class MoveTutorCMD implements CoreCommand {
    public static final String SELECTION_TYPE = "admin";

    public static final MoveTutorCMD INSTANCE = new MoveTutorCMD();

    private MoveTutorCMD() {}

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection context) {
        dispatcher.register(
                CommandBuilder.create("move-tutor")
                        .requires(src -> PermissionRegistry.checkPermission(src, CobblemonMoveTutor.INSTANCE.getPermissions().MOVE_TUTOR_PERMISSION))
                        .executes(this::action)
                        .then("other", other -> other
                                        .requires(src ->
                                                PermissionRegistry.checkPermission(src, CobblemonMoveTutor.INSTANCE.getPermissions().MOVE_TUTOR_OTHER_PERMISSION))
                                        .argument("player", EntityArgument.player(), player -> player
                                                .executes(this::other))
                        )
                        .build()
        );
    }

    public int action(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) return 0;
        TutorMenuProvider.open.pokemonSelectionMenu(player, SELECTION_TYPE);
        return 1;
    }

    private int other(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "player");
        TutorMenuProvider.open.pokemonSelectionMenu(targetPlayer, SELECTION_TYPE);
        if (player != null)
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.cmd.openedForOther", targetPlayer.getDisplayName().getString()));
        return 1;
    }
}