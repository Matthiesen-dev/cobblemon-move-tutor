package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StatsRegistry {
    public static void init() {}

    private static <T extends ResourceLocation> Supplier<T> register(String id, Supplier<T> stats) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerStats(id, stats);
    }
}
