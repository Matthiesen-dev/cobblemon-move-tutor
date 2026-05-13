package dev.matthiesen.neoforge.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommonClient;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class CobblemonMoveTutorNeoForgeClient {
    public CobblemonMoveTutorNeoForgeClient(IEventBus modBus) {
        CobblemonMoveTutorCommonClient.initialize();
        modBus.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void registerScreens(RegisterMenuScreensEvent event) {
        CobblemonMoveTutorCommonClient.registerMenuScreens();
    }
}
