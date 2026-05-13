package dev.matthiesen.common.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.ConfirmationScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.PokemonSelectionScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.SelectMoveScreen;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLibClient;

public class CobblemonMoveTutorCommonClient {
    public static void initialize() {
        Constants.createInfoLog("Initializing client logic");
    }

    public static void registerMenuScreens() {
        MatthiesenLibClient.registerMenuScreen(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), ConfirmationScreen::new);
        MatthiesenLibClient.registerMenuScreen(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), PokemonSelectionScreen::new);
        MatthiesenLibClient.registerMenuScreen(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), SelectMoveScreen::new);
    }
}
