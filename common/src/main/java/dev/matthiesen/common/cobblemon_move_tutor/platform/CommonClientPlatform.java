package dev.matthiesen.common.cobblemon_move_tutor.platform;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface CommonClientPlatform {
    @FunctionalInterface
    interface ScreenRegistrar {
        <M extends AbstractContainerMenu, S extends Screen & MenuAccess<M>>
        void register(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, S> screenConstructor);
    }

    /**
     * Register all menu screens for the current loader.
     * Fabric can do this immediately, while NeoForge can hook the proper mod event.
     */
    void registerMenuScreens(ScreenRegistrar registrar);
}
