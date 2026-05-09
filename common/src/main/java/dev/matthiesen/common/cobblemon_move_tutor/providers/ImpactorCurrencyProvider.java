package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.ICurrencyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ImpactorCurrencyProvider implements ICurrencyProvider {
    @Override
    public @NotNull Component lore(int price) {
        return null;
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        return false;
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {

    }
}
