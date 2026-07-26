package dev.matthiesen.cobblemon_move_tutor.common;

import dev.matthiesen.cobblemon_move_tutor.common.registry.MenuTypesRegistry;
import dev.matthiesen.cobblemon_move_tutor.common.ui.client.ConfirmationScreen;
import dev.matthiesen.cobblemon_move_tutor.common.ui.client.PokemonSelectionScreen;
import dev.matthiesen.cobblemon_move_tutor.common.ui.client.SelectMoveScreen;
import dev.matthiesen.matthiesen_core.common.AbstractCommonClientMod;

public final class CobblemonMoveTutorClient extends AbstractCommonClientMod {
    public static final CobblemonMoveTutorClient INSTANCE = new CobblemonMoveTutorClient();

    public CobblemonMoveTutorClient() {
        super(CobblemonMoveTutor.INSTANCE);
    }

    public void initialize() {
        createInfoLog("Initializing client logic");

        INSTANCE.getScreenManager().registerMenuScreens(registry -> {
            registry.register(MenuTypesRegistry.CONFIRMATION_SCREEN, ConfirmationScreen::new);
            registry.register(MenuTypesRegistry.SELECT_MOVE_SCREEN, SelectMoveScreen::new);
            registry.register(MenuTypesRegistry.POKEMON_SELECTION_SCREEN, PokemonSelectionScreen::new);
        });
    }
}
