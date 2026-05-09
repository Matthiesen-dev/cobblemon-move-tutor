package dev.matthiesen.neoforge.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommonClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class CobblemonMoveTutorNeoForgeClient {
    public CobblemonMoveTutorNeoForgeClient(IEventBus modBus) {
        Constants.createInfoLog("Loading client-side for NeoForge Mod Loader");
        modBus.addListener(this::clientSetup);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        CobblemonMoveTutorCommonClient.initialize();
    }
}
