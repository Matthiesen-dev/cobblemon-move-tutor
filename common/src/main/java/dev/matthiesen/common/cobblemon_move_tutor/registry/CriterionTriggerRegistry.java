package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public class CriterionTriggerRegistry {
    public static void init() {}

    private static <T extends CriterionTrigger<?>>Supplier<T> register(String id, Supplier<T> criterionTrigger) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerCriteriaTriggers(id, criterionTrigger);
    }
}
