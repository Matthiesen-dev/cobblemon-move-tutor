package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CurrencyProvidersConfig;
import fr.harmex.cobbledollars.common.utils.extensions.PlayerExtensionKt;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CobbleDollarsCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "cobbledollars";
    }

    @Override
    public String currencyDisplayName() {
        return getConfig().currencyDisplayName;
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        BigInteger balance = PlayerExtensionKt.getCobbleDollars(player);

        if (balance.intValue() < price) {
            return notEnoughFunds(player, price);
        }

        PlayerExtensionKt.setCobbleDollars(player, balance.subtract(new BigDecimal(price).toBigInteger()));
        return true;
    }

    private CurrencyProvidersConfig.CobbleDollarsProvider getConfig() {
        return CobblemonMoveTutorCommon.getCurrencyProvidersConfig().cobbleDollarsProvider;
    }
}
