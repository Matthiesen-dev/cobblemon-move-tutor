package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.item.GuiItem;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
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
        return MatthiesenLib.registerItem(Constants.modResource(id), item);
    }
}
