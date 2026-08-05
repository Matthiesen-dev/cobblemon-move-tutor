package dev.matthiesen.cobblemon_move_tutor.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class PermissionsConfig {

    // Permissions Config
    public ModConfigSpec.IntValue permissionLevels_cmdMoveTutor;
    public ModConfigSpec.IntValue permissionLevels_cmdMoveTutor_other;

    public PermissionsConfig(ModConfigSpec.Builder builder) {
        builder.comment("Permissions Config").push("permissionLevelsConfig");
        permissionLevels_cmdMoveTutor = builder.comment("Permission level required to use the /movetutor command.")
                .defineInRange("cmdMoveTutor", 4, 0, 4);
        permissionLevels_cmdMoveTutor_other = builder.comment("Permission level required to use the /movetutor command on other players.")
                .defineInRange("cmdMoveTutor_other", 4, 0, 4);
        builder.pop();
    }
}
