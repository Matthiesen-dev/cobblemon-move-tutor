package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.platform.CommonClientPlatform;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.ConfirmationScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.PokemonSelectionScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.SelectMoveScreen;

public final class MenuScreenRegistry {
    public static void registerAll(CommonClientPlatform.ScreenRegistrar registrar) {
        registrar.register(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), ConfirmationScreen::new);
        registrar.register(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), PokemonSelectionScreen::new);
        registrar.register(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), SelectMoveScreen::new);
    }
}
