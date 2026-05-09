package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.config.CurrencyProvidersConfig;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.kyori.adventure.key.Key;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ImpactorCurrencyProvider extends AbstractCurrencyProvider {
    private static final EconomyService ECONOMY_SERVICE = EconomyService.instance();

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
        Account account = getAccount(player.getUUID());

        if (account.balance().intValue() < price) {
            return notEnoughFunds(player, price);
        }

        return account.withdraw(new BigDecimal(price)).successful();
    }

    private Currency getCurrency() {
        String impactorCurrency = getConfig().impactorCurrency;
        Optional<Currency> currency = ECONOMY_SERVICE.currencies().currency(Key.key(impactorCurrency));
        if (currency.isEmpty()) {
            Constants.LOGGER.warn("Impactor currency '{}' not found, defaulting to primary currency", impactorCurrency);
            return ECONOMY_SERVICE.currencies().primary();
        }
        return currency.get();
    }

    private CurrencyProvidersConfig.ImpactorProvider getConfig() {
        return CobblemonMoveTutorCommon.currencyProvidersConfig.impactorProvider;
    }

    private Account getAccount(@NotNull UUID playerUUID) {
        if (!ECONOMY_SERVICE.hasAccount(playerUUID).join()) {
            return ECONOMY_SERVICE.account(playerUUID).join();
        }

        return ECONOMY_SERVICE.account(getCurrency(), playerUUID).join();
    }
}
