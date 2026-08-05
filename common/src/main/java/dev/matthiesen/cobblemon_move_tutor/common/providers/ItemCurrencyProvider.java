package dev.matthiesen.cobblemon_move_tutor.common.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.MoveTutorConfig;
import dev.matthiesen.matthiesen_core.common.api.economy.BuiltInEconomyProviders;
import dev.matthiesen.matthiesen_core.common.api.economy.EconomyProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public final class ItemCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "item";
    }

    @Override
    public String currencyDisplayName() {
        return MoveTutorConfig.SERVER_CONFIG.currencyProvider_item_displayName.get();
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        EconomyProvider ECONOMY_PROVIDER = CobblemonMoveTutor.INSTANCE.getEconomyManager().getEconomyProvider(BuiltInEconomyProviders.ITEM);
        try {
            if (!ECONOMY_PROVIDER.hasEnough(player, price, MoveTutorConfig.SERVER_CONFIG.currencyProvider_item_itemId.get())) {
                return notEnoughFunds(player, price);
            }

            return ECONOMY_PROVIDER.withdraw(player, price, MoveTutorConfig.SERVER_CONFIG.currencyProvider_item_itemId.get());
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("Error processing ItemCurrencyProvider transaction for player %player%"
                    .replace("%player%", player.getDisplayName().getString()), e);
            return false;
        }
    }
}
