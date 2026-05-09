package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SoundRegistry {
    public static void init() {}

    @SuppressWarnings("SameParameterValue")
    private static <T extends SoundEvent> Supplier<T> register(String id, Supplier<T> sound) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerSound(id, sound);
    }
}
