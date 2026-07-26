package dev.matthiesen.cobblemon_move_tutor.neoforge;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutorClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = CobblemonMoveTutor.MOD_ID, dist = Dist.CLIENT)
public final class CobblemonMoveTutorNeoForgeClient {
    public CobblemonMoveTutorNeoForgeClient() {
        var instance = CobblemonMoveTutorClient.INSTANCE;
        instance.initialize();
    }
}
