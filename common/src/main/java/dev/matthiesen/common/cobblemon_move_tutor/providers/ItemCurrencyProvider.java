package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

// TODO: Implement this
public class ItemCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "item";
    }

    @Override
    public String currencyDisplayName() {
        return "Rare Candy"; // TODO Setup a config option for this
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        return false;
    }
}
