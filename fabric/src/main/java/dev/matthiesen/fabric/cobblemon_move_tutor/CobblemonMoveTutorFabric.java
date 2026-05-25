package dev.matthiesen.fabric.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.fabricmc.api.ModInitializer;

public class CobblemonMoveTutorFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.createInfoLog("Loading for Fabric Mod Loader");
        CobblemonMoveTutorCommon.loadConfig();
        CobblemonMoveTutorCommon.initialize();
    }
}
