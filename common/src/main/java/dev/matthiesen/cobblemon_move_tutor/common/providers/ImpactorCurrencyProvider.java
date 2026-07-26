package dev.matthiesen.cobblemon_move_tutor.common.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.CurrencyProvidersConfig;
import dev.matthiesen.matthiesen_core.common.api.economy.BuiltInEconomyProviders;
import dev.matthiesen.matthiesen_core.common.api.economy.EconomyProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public final class ImpactorCurrencyProvider extends AbstractCurrencyProvider {
    private static final EconomyProvider ECONOMY_PROVIDER = CobblemonMoveTutor.INSTANCE.getEconomyManager().getEconomyProvider(BuiltInEconomyProviders.IMPACTOR);

    @Override
    public String currencyName() {
        return "impactor";
    }

    @Override
    public String currencyDisplayName() {
        return getConfig().currencyDisplayName;
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        try {
            if (!ECONOMY_PROVIDER.hasEnough(player, price, getConfig().impactorCurrency)) {
                return notEnoughFunds(player, price);
            }

            return ECONOMY_PROVIDER.withdraw(player, price, getConfig().impactorCurrency);
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("Error processing Impactor transaction for player %player%"
                    .replace("%player%", player.getDisplayName().getString()), e);
            return false;
        }
    }

    private CurrencyProvidersConfig.ImpactorProvider getConfig() {
        return CobblemonMoveTutor.INSTANCE.getCurrencyProvidersConfig().impactorProvider;
    }
}
