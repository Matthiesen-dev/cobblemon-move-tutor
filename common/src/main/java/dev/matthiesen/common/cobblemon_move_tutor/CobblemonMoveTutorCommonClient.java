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
        MatthiesenLibClient.registerMenuScreens(registry -> {
            registry.register(MenuTypesRegistry.CONFIRMATION_SCREEN, ConfirmationScreen::new);
            registry.register(MenuTypesRegistry.SELECT_MOVE_SCREEN, SelectMoveScreen::new);
            registry.register(MenuTypesRegistry.POKEMON_SELECTION_SCREEN, PokemonSelectionScreen::new);
        });
    }
}
