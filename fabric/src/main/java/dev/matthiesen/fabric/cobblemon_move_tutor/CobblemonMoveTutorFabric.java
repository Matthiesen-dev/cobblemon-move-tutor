package dev.matthiesen.fabric.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class CobblemonMoveTutorFabric implements ModInitializer {
    public static MinecraftServer MC_SERVER;

    @Override
    public void onInitialize() {
        Constants.createInfoLog("Loading for Fabric Mod Loader");
        CobblemonMoveTutorCommon.loadConfig();
        CobblemonMoveTutorCommon.initialize();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            MC_SERVER = server;
            CobblemonMoveTutorCommon.onStartup();
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            MC_SERVER = null;
            CobblemonMoveTutorCommon.onShutdown();
        });
    }
}
