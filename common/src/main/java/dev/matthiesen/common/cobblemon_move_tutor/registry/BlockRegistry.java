package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockRegistry {
    public static void init() {}

    // Registration
    private static <T extends Block> Supplier<T> register(String id, Supplier<T> block) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerBlock(id, block);
    }
}
