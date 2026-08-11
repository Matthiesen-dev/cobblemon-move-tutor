package dev.matthiesen.cobblemon_move_tutor.neoforge;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutorClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = CobblemonMoveTutor.MOD_ID, dist = Dist.CLIENT)
public final class CobblemonMoveTutorNeoForgeClient {
    private static final CobblemonMoveTutorClient INSTANCE = CobblemonMoveTutorClient.INSTANCE;

    public CobblemonMoveTutorNeoForgeClient(IEventBus modBus) {
        INSTANCE.initialize();
        modBus.addListener(this::registerScreens);
    }

    public void registerScreens(RegisterMenuScreensEvent event) {
        INSTANCE.registerScreens();
    }
}
