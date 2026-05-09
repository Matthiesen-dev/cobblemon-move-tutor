package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;

// TODO
public class PermissionsConfig {
    @SerializedName("permissionlevels")
    public PermissionLevels permissionLevels = new PermissionLevels();

    public static class PermissionLevels {
        @SerializedName("command.move-tutor")
        public int COMMAND_MOVE_TUTOR_PERMISSION_LEVEL =
                Constants.PERMISSION_LEVELS.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();

        @SerializedName("command.move-tutor.other")
        public int COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL =
                Constants.PERMISSION_LEVELS.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();
    }

    @SuppressWarnings("unused")
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
}
