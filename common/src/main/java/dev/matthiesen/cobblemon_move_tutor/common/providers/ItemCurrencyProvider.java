package dev.matthiesen.cobblemon_move_tutor.common.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.CurrencyProvidersConfig;
import dev.matthiesen.matthiesen_core.common.api.economy.BuiltInEconomyProviders;
import dev.matthiesen.matthiesen_core.common.api.economy.EconomyProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public final class ItemCurrencyProvider extends AbstractCurrencyProvider {
    private static final EconomyProvider ECONOMY_PROVIDER = CobblemonMoveTutor.INSTANCE.getEconomyManager().getEconomyProvider(BuiltInEconomyProviders.ITEM);

    @Override
    public String currencyName() {
        return getConfig().currencyDisplayName;
    }

    @Override
    public String currencyDisplayName() {
        return getConfig().currencyDisplayName;
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        try {
            var config = getConfig();
            if (!ECONOMY_PROVIDER.hasEnough(player, price, config.itemId)) {
                return notEnoughFunds(player, price);
            }
            return ECONOMY_PROVIDER.withdraw(player, price, config.itemId);
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("Error processing ItemCurrencyProvider transaction for player %player%"
                    .replace("%player%", player.getDisplayName().getString()), e);
            return false;
        }
    }

    private CurrencyProvidersConfig.ItemProvider getConfig() {
        return CobblemonMoveTutor.INSTANCE.getCurrencyProvidersConfig().itemProvider;
    }
}
