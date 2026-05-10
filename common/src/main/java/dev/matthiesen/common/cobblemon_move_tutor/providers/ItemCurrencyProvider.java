package dev.matthiesen.common.cobblemon_move_tutor.providers;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CurrencyProvidersConfig;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemDecoder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ItemCurrencyProvider extends AbstractCurrencyProvider {
    @Override
    public String currencyName() {
        return "item";
    }

    @Override
    public String currencyDisplayName() {
        return getConfig().currencyDisplayName;
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        var config = getConfig();
        Item currencyItem = ItemDecoder.stringToItem(config.itemId, CobblemonItems.RARE_CANDY);
        if (currencyItem == Items.AIR) {
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.gui.invalidCurrencyItem", config.itemId).withStyle(ChatFormatting.RED));
            UIManager.closeUI(player);
            return false;
        }

        if (getPlayerBalance(player, currencyItem) < price) {
            return notEnoughFunds(player, price);
        }

        subtractPlayerBalance(player, currencyItem, price);
        return true;
    }

    private int getPlayerBalance(ServerPlayer player, Item currencyItem) {
        int balance = 0;
        for (var itemStack : player.getInventory().items) {
            if (itemStack.getItem() == currencyItem) {
                balance += itemStack.getCount();
            }
        }
        return balance;
    }

    private void subtractPlayerBalance(ServerPlayer player, Item currencyItem, int amount) {
        int remaining = amount;
        for (var itemStack : player.getInventory().items) {
            if (itemStack.getItem() == currencyItem) {
                int count = itemStack.getCount();
                if (count >= remaining) {
                    itemStack.shrink(remaining);
                    break;
                } else {
                    itemStack.setCount(0);
                    remaining -= count;
                }
            }
        }
    }

    private CurrencyProvidersConfig.ItemProvider getConfig() {
        return CobblemonMoveTutorCommon.getCurrencyProvidersConfig().itemProvider;
    }
}
