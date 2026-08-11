package dev.matthiesen.cobblemon_move_tutor.common.registry;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.item.GuiItem;
import dev.matthiesen.matthiesen_core.common.registry.AbstractItemRegistry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public final class ItemRegistry extends AbstractItemRegistry {
    private static final ItemRegistry INSTANCE = new ItemRegistry();

    public ItemRegistry() {
        super(CobblemonMoveTutor.MOD_ID);
    }

    public static void init() {}

    public static final Supplier<Item> GUI_ITEM;

    static {
        GUI_ITEM = INSTANCE.register("gui_item", GuiItem::new);
    }
}
