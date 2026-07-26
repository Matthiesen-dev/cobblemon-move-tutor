package dev.matthiesen.cobblemon_move_tutor.fabric;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutorClient;
import net.fabricmc.api.ClientModInitializer;

public final class CobblemonMoveTutorFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        var instance = CobblemonMoveTutorClient.INSTANCE;
        instance.initialize();
    }
}
