package dev.matthiesen.cobblemon_move_tutor.neoforge;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import net.neoforged.fml.common.Mod;

@Mod(CobblemonMoveTutor.MOD_ID)
public final class CobblemonMoveTutorNeoForge {
    public CobblemonMoveTutorNeoForge() {
        var instance = CobblemonMoveTutor.INSTANCE;
        instance.createInfoLog("Loading for NeoForge Mod Loader");
        instance.initialize();
    }
}
