package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib.permission.PermissionLevel;

public class PermissionsConfig {
    @SerializedName("permissionlevels")
    public PermissionLevels permissionLevels = new PermissionLevels();

    public static class PermissionLevels {
        @SerializedName("command.move-tutor")
        public int COMMAND_MOVE_TUTOR_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getNumericalValue();

        @SerializedName("command.move-tutor.other")
        public int COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getNumericalValue();

        @SerializedName("command.move-tutor.reload")
        public int COMMAND_MOVE_TUTOR_RELOAD_PERMISSION_LEVEL =
                PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getNumericalValue();
    }

    @SuppressWarnings("unused")
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
}
