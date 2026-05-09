package dev.matthiesen.common.cobblemon_move_tutor.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemDecoder {
    public static Item stringToItem(String string) {
        ResourceLocation rsLoc = ResourceLocation.parse(string);
        return BuiltInRegistries.ITEM.get(rsLoc);
    }
}
