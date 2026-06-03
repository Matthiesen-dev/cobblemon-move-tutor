package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.item.GuiItem;
import dev.matthiesen.common.matthiesen_lib.registry.AbstractItemRegistry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public final class ItemRegistry extends AbstractItemRegistry {
    private static final ItemRegistry INSTANCE = new ItemRegistry();

    public ItemRegistry() {
        super(Constants.MOD_ID);
    }

    public static void init() {}

    public static final Supplier<Item> GUI_ITEM;

    static {
        GUI_ITEM = INSTANCE.register("gui_item", GuiItem::new);
    }
}
