package dev.matthiesen.common.cobblemon_move_tutor.util;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ItemDecoder {
    public static Item stringToItem(String string, Item fallback) {
        ResourceLocation rsLoc = ResourceLocation.parse(string);
        Item item = BuiltInRegistries.ITEM.get(rsLoc);
        if (item == Items.AIR) {
            var fallbackKey = BuiltInRegistries.ITEM.getKey(fallback);
            Constants.createErrorLog("Failed to decode item from string: " + string + ". Defaulting to fallback item: " + fallbackKey);
            return fallback;
        }
        return item;
    }
}
