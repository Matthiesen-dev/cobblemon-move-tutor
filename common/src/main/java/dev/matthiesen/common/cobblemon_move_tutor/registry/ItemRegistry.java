package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.item.GuiItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemRegistry {
    public static void init() {}

    public static Supplier<Item> GUI_ITEM;

    static {
        GUI_ITEM = register("gui_item", GuiItem::new);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> Supplier<T> register(String id, Supplier<T> item) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerItem(id, item);
    }
}
