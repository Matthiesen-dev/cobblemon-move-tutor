package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class ImpactorCurrencyProvider extends AbstractCurrencyProvider {
    private static final EconomyService ECONOMY_SERVICE = EconomyService.instance();
    private static final Currency CURRENCY = ECONOMY_SERVICE.currencies().primary();

    @Override
    public String currencyName() {
        return "impactor";
    }

    @Override
    public String currencyDisplayName() {
        return "Impactor Dollars"; // TODO Setup a config option for this
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        Account account = getAccount(player.getUUID());

        if (account.balance().intValue() < price) {
            return notEnoughFunds(player, price);
        }

        return account.withdraw(new BigDecimal(price)).successful();
    }

    private Account getAccount(@NotNull UUID playerUUID) {
        if (!ECONOMY_SERVICE.hasAccount(playerUUID).join()) {
            return ECONOMY_SERVICE.account(playerUUID).join();
        }

        return ECONOMY_SERVICE.account(CURRENCY, playerUUID).join();
    }
}
