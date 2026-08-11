package dev.matthiesen.cobblemon_move_tutor.common.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.MoveTutorConfig;
import fr.harmex.cobbledollars.common.utils.extensions.PlayerExtensionKt;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class CobbleDollarsCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "cobbledollars";
    }

    @Override
    public String currencyDisplayName() {
        return MoveTutorConfig.SERVER_CONFIG.currencyProvider_cobbleDollars_displayName.get();
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        try {
            BigInteger balance = PlayerExtensionKt.getCobbleDollars(player);

            if (balance.intValue() < price) {
                return notEnoughFunds(player, price);
            }

            PlayerExtensionKt.setCobbleDollars(player, balance.subtract(new BigDecimal(price).toBigInteger()));
            return true;
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("Error processing CobbleDollars transaction for player %player%"
                    .replace("%player%", player.getDisplayName().getString()), e);
            return false;
        }
    }
}
