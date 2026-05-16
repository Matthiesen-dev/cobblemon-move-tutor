package dev.matthiesen.neoforge.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CobblemonMoveTutorNeoForge {
    public CobblemonMoveTutorNeoForge() {
        Constants.createInfoLog("Loading for NeoForge Mod Loader");
        CobblemonMoveTutorCommon.loadConfig();
        CobblemonMoveTutorCommon.initialize();
    }
}
