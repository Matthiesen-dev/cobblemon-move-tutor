package dev.matthiesen.cobblemon_move_tutor.common.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.CurrencyProvidersConfig;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.kyori.adventure.key.Key;
import net.minecraft.server.level.ServerPlayer;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class ImpactorCurrencyProvider extends AbstractCurrencyProvider {
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
        try {
            Account account = getAccount(player.getUUID());

            if (account.balance().intValue() < price) {
                return notEnoughFunds(player, price);
            }

            return account.withdraw(new BigDecimal(price)).successful();
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("Error processing Impactor transaction for player %player%"
                    .replace("%player%", player.getDisplayName().getString()), e);
            return false;
        }
    }

    private Currency getCurrency() {
        @Subst("impactor:dollars")
        String impactorCurrency = getConfig().impactorCurrency;
        Optional<Currency> currency = ECONOMY_SERVICE.currencies().currency(Key.key(impactorCurrency));
        if (currency.isEmpty()) {
            CobblemonMoveTutor.INSTANCE.createWarnLog("Impactor currency " + impactorCurrency + " not found, defaulting to primary currency");
            return ECONOMY_SERVICE.currencies().primary();
        }
        return currency.get();
    }

    private CurrencyProvidersConfig.ImpactorProvider getConfig() {
        return CobblemonMoveTutor.INSTANCE.getCurrencyProvidersConfig().impactorProvider;
    }

    private Account getAccount(@NotNull UUID playerUUID) {
        if (!ECONOMY_SERVICE.hasAccount(playerUUID).join()) {
            return ECONOMY_SERVICE.account(playerUUID).join();
        }

        return ECONOMY_SERVICE.account(getCurrency(), playerUUID).join();
    }
}
