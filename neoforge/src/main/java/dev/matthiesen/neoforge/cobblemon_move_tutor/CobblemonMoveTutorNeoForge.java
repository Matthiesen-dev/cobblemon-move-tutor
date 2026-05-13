package dev.matthiesen.neoforge.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod(Constants.MOD_ID)
public class CobblemonMoveTutorNeoForge {
    public static volatile MinecraftServer MC_SERVER;

    public CobblemonMoveTutorNeoForge() {
        Constants.createInfoLog("Loading for NeoForge Mod Loader");
        CobblemonMoveTutorCommon.loadConfig();
        CobblemonMoveTutorCommon.initialize();
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MC_SERVER = event.getServer();
        CobblemonMoveTutorCommon.onStartup();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onServerStopping(ServerStoppingEvent event) {
        MC_SERVER = null;
        CobblemonMoveTutorCommon.onShutdown();
    }
}
