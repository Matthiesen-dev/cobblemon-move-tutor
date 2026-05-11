package dev.matthiesen.common.cobblemon_move_tutor.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemBuilder {
    private final ItemStack stack;

    public ItemBuilder(Item item) {
        this.stack = new ItemStack(item);
    }

    public ItemBuilder(ItemStack item) {
        this.stack = item;
    }

    public ItemBuilder addLore(Component[] newLore) {
        ItemLore itemLore = stack.get(DataComponents.LORE);
        if (itemLore == null) {
            itemLore = new ItemLore(List.of());
        }

        List<Component> list = Stream.concat(itemLore.lines().stream(), Arrays.stream(newLore))
                .collect(Collectors.toList());

        // Go through every Component, and get the current style and set Italic to false
        list = list.stream()
                .map(component ->
                        component.copy().withStyle(component.getStyle().withItalic(false))
                )
                .collect(Collectors.toList());

        itemLore = new ItemLore(list);
        stack.set(DataComponents.LORE, itemLore);
        return this;
    }

    public ItemBuilder setModelData(int value) {
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(value));
        return this;
    }

    public ItemBuilder hideAdditional() {
        stack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        stack.set(DataComponents.RARITY, Rarity.COMMON);
        return this;
    }

    public ItemBuilder setCustomName(Component customName) {
        stack.set(DataComponents.CUSTOM_NAME, customName.copy().withStyle(customName.getStyle().withItalic(false)));
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }
}
