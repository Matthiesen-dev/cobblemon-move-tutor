package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.ICurrencyProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCurrencyProvider implements ICurrencyProvider {
    public abstract String currencyName();
    public abstract String currencyDisplayName();

    public boolean notEnoughFunds(ServerPlayer player, int price) {
        player.sendSystemMessage(
                Component.translatable(
                        "cobblemon_move_tutor.gui.notEnoughMoney",
                        String.valueOf(price),
                        currencyDisplayName()
                ).withStyle(ChatFormatting.RED));
        return false;
    }

    @Override
    public @NotNull Component lore(int price) {
        return Component.translatable("cobblemon_move_tutor.gui.priceLore", String.valueOf(price), currencyName());
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        if (price > 0) {
            player.sendSystemMessage(
                    Component.translatable(
                            "cobblemon_move_tutor.gui.buyMove",
                            move.getDisplayName(),
                            pokemon.getDisplayName(false).getString(),
                            String.valueOf(price),
                            currencyDisplayName()
                    ).withStyle(ChatFormatting.GREEN)
            );
        } else {
            player.sendSystemMessage(
                    Component.translatable(
                            "cobblemon_move_tutor.gui.buyMoveFree",
                            move.getDisplayName(),
                            pokemon.getDisplayName(false).getString()
                    ).withStyle(ChatFormatting.GREEN)
            );
        }
    }
}
