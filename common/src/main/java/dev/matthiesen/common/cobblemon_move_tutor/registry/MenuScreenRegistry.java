package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.ui.client.ConfirmationScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.PokemonSelectionScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.SelectMoveScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class MenuScreenRegistry {
    private MenuScreenRegistry() {
    }

    @FunctionalInterface
    public interface Registrar {
        <M extends AbstractContainerMenu, S extends Screen & net.minecraft.client.gui.screens.inventory.MenuAccess<M>>
        void register(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, S> screenConstructor);
    }

    public static void registerAll(Registrar registrar) {
        registrar.register(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), ConfirmationScreen::new);
        registrar.register(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), PokemonSelectionScreen::new);
        registrar.register(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), SelectMoveScreen::new);
    }
}
