package dev.matthiesen.common.cobblemon_move_tutor.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuConstructor;

public class TutorMenuProvider {
    public static SimpleMenuProvider create(MenuConstructor constructor) {
        return new SimpleMenuProvider(constructor, Component.empty());
    }
}
