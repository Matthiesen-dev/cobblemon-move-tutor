package dev.matthiesen.cobblemon_move_tutor.common.config;

import dev.matthiesen.matthiesen_core.common.api.permissions.PermissionLevel;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class PermissionsConfig {

    // Permissions Config
    public ModConfigSpec.EnumValue<PermissionLevel> permissionLevels_cmdMoveTutor;
    public ModConfigSpec.EnumValue<PermissionLevel> permissionLevels_cmdMoveTutor_other;

    public PermissionsConfig(ModConfigSpec.Builder builder) {
        builder.comment("Permissions Config").push("permissionLevelsConfig");
        permissionLevels_cmdMoveTutor = builder.comment("Permission level required to use the /movetutor command.")
                .defineEnum("cmdMoveTutor", PermissionLevel.ALL_COMMANDS);
        permissionLevels_cmdMoveTutor_other = builder.comment("Permission level required to use the /movetutor command on other players.")
                .defineEnum("cmdMoveTutor_other", PermissionLevel.ALL_COMMANDS);
        builder.pop();
    }
}
