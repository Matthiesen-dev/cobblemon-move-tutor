package dev.matthiesen.common.cobblemon_move_tutor.providers;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import fr.harmex.cobbledollars.common.utils.extensions.PlayerExtensionKt;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class CobbleDollarsCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "cobbledollars";
    }

    @Override
    public String currencyDisplayName() {
        return "Cobble Dollars";
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        var balance = PlayerExtensionKt.getCobbleDollars(player);

        if (balance.intValue() < price) {
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.gui.notEnoughMoney", String.valueOf(price), currencyDisplayName()));
            return false;
        }

        PlayerExtensionKt.setCobbleDollars(player, balance.subtract(new BigDecimal(price).toBigInteger()));
        return true;
    }
}
