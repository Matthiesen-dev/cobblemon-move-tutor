package dev.matthiesen.cobblemon_move_tutor.common.config;

import com.google.gson.annotations.SerializedName;
import dev.matthiesen.matthiesen_core.common.api.permissions.PermissionLevel;

public final class PermissionsConfig {
    @SerializedName("permissionlevels")
    public PermissionLevels permissionLevels = new PermissionLevels();

    public static class PermissionLevels {
        @SerializedName("command.move-tutor")
        public int COMMAND_MOVE_TUTOR_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();

        @SerializedName("command.move-tutor.other")
        public int COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();

        @SerializedName("command.move-tutor.reload")
        public int COMMAND_MOVE_TUTOR_RELOAD_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();
    }
}
