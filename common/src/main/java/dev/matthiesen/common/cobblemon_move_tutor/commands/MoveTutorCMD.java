package dev.matthiesen.common.cobblemon_move_tutor.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.gui.PlayerPokemonSelectionGui;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.ICommand;
import dev.matthiesen.common.cobblemon_move_tutor.permissions.ModPermissions;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MoveTutorCMD implements ICommand {
    public static final String SELECTION_TYPE = "admin";

    public MoveTutorCMD() {}

    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection context) {
        dispatcher.register(
                Commands.literal("move-tutor")
                        .requires(src -> ModPermissions.checkPermission(
                                src,
                                CobblemonMoveTutorCommon.permissions.MOVE_TUTOR_PERMISSION
                        ))
                        .executes(this::self)
                        .then(
                                Commands.literal("other")
                                        .requires(src -> ModPermissions.checkPermission(
                                                src,
                                                CobblemonMoveTutorCommon.permissions.MOVE_TUTOR_OTHER_PERMISSION
                                        ))
                                        .then(
                                                Commands.argument("player", EntityArgument.player())
                                                        .executes(this::other)
                                        )
                        )
        );
    }

    private int self(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) return 0;
        new PlayerPokemonSelectionGui(player, SELECTION_TYPE).open();
        return 1;
    }

    private int other(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayer();
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "player");
        if (player == null) return 0;
        new PlayerPokemonSelectionGui(targetPlayer, SELECTION_TYPE).open();
        player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.cmd.openedForOther", targetPlayer.getDisplayName().getString()));
        return 1;
    }
}