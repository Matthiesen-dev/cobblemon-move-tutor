package dev.matthiesen.fabric.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommonClient;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class CobblemonMoveTutorFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CobblemonMoveTutorCommonClient.initialize();
        CobblemonMoveTutorCommonClient.registerMenuScreens(MenuScreens::register);
    }
}
