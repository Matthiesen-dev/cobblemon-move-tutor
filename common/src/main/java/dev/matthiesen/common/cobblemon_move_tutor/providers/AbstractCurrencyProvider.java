package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.ICurrencyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCurrencyProvider implements ICurrencyProvider {
    public abstract String currencyName();
    public abstract String currencyDisplayName();

    @Override
    public @NotNull Component lore(int price) {
        return Component.translatable("gui.cobblemon_move_tutor.priceLore", String.valueOf(price), currencyName());
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        if (price > 0) {
            player.sendSystemMessage(
                    Component.translatable(
                            "gui.cobblemon_move_tutor.buyMove",
                            move.getDisplayName(),
                            pokemon.getDisplayName(false).getString(),
                            String.valueOf(price),
                            currencyDisplayName()
                    )
            );
        } else {
            player.sendSystemMessage(
                    Component.translatable(
                            "gui.cobblemon_move_tutor.buyMoveFree",
                            move.getDisplayName(),
                            pokemon.getDisplayName(false).getString()
                    )
            );
        }
    }
}
