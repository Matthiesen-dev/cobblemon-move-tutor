package dev.matthiesen.cobblemon_move_tutor.fabric;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import net.fabricmc.api.ModInitializer;

public final class CobblemonMoveTutorFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        var instance = CobblemonMoveTutor.INSTANCE;
        instance.createInfoLog("Loading for Fabric Mod Loader");
        instance.initialize();
    }
}
