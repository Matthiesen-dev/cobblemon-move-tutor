package dev.matthiesen.common.cobblemon_move_tutor.ui.buttons;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ModelData;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class StaticButtons {
    public static ItemBuilder getBaseGuiItem(int model) {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .hideAdditional()
                .setModelData(model);
    }

    public static ItemStack buildPrevItem() {
        return getBaseGuiItem(ModelData.GUI_BUTTON.GUI_PREVIOUS)
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.previousPage").withStyle(ChatFormatting.AQUA))
                .build();
    }

    public static ItemStack buildNextItem() {
        return getBaseGuiItem(ModelData.GUI_BUTTON.GUI_NEXT)
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.nextPage").withStyle(ChatFormatting.AQUA))
                .build();
    }

    public static ItemStack buildPageItem(int current, int total) {
        return getBaseGuiItem(ModelData.GUI_BUTTON.GUI_PAGE)
                .setCustomName(Component.translatable(
                        "cobblemon_move_tutor.gui.button.pageIndicator",
                        String.valueOf(current),
                        String.valueOf(total)
                ).withStyle(ChatFormatting.GOLD))
                .build();
    }

    public static ItemStack buildCancelItem() {
        return getBaseGuiItem(ModelData.GUI_BUTTON.GUI_CANCEL)
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.cancel")
                        .withStyle(ChatFormatting.RED))
                .build();
    }

    public static ItemStack buildConfirmItem() {
        return getBaseGuiItem(ModelData.GUI_BUTTON.GUI_CONFIRM)
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.confirm")
                        .withStyle(ChatFormatting.GREEN))
                .build();
    }

    public static ItemStack buildTitleItem(int modelData) {
        return getBaseGuiItem(modelData)
                .setCustomName(Component.literal(" "))
                .build();
    }

    public static ItemStack getEmptyPokemonItem() {
        return new ItemBuilder(CobblemonItems.POKE_BALL)
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.emptySlot").withStyle(ChatFormatting.GRAY))
                .hideAdditional()
                .build();
    }

    public static ItemStack getMoveItem(MoveTemplate move, Pokemon selectedPokemon) {
        return new ItemBuilder(PokemonUtility.getItemTM(move))
                .hideAdditional()
                .setCustomName(move.getDisplayName())
                .addLore(PokemonUtility.getMoveLore(selectedPokemon, move))
                .build();
    }
}
