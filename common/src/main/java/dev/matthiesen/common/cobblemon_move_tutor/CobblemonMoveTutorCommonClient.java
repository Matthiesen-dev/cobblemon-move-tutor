package dev.matthiesen.common.cobblemon_move_tutor;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.ConfirmationScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.PokemonSelectionScreen;
import dev.matthiesen.common.cobblemon_move_tutor.ui.client.SelectMoveScreen;

public class CobblemonMoveTutorCommonClient {
    public static void initialize() {
        Constants.createInfoLog("Initializing client logic");

        // For some reason this only works on Fabric so we have to manually add it to the client events on NeoForge instead
        if (Platform.isFabric()) {
            ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
                MenuRegistry.registerScreenFactory(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), ConfirmationScreen::new);
                MenuRegistry.registerScreenFactory(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), PokemonSelectionScreen::new);
                MenuRegistry.registerScreenFactory(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), SelectMoveScreen::new);
            });
        }
    }
}
