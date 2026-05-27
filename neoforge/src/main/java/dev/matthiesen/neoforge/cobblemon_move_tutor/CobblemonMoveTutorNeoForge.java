package dev.matthiesen.neoforge.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

@Mod(Constants.MOD_ID)
public class CobblemonMoveTutorNeoForge {
    public CobblemonMoveTutorNeoForge() {
        Constants.createInfoLog("Loading for NeoForge Mod Loader");
        CobblemonMoveTutorCommon.loadConfig();
        CobblemonMoveTutorCommon.initialize();
    }
}
