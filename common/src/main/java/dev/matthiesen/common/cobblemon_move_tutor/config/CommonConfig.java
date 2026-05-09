package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.annotations.SerializedName;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;

public class CommonConfig extends ConfigBase {
    @SerializedName("currencyConfig")
    public CurrencyConfig currencyConfig = new CurrencyConfig();

    @SerializedName("villageTutorConfig")
    public TutorConfig villageTutorConfig = new TutorConfig();

    @SerializedName("adminTutorConfig")
    public TutorConfig adminTutorConfig = new TutorConfig();

    public static class CurrencyConfig {
        @SerializedName("currencyType")
        public String currencyType = "item";

        @SerializedName("levelMovePrice")
        public int levelMovePrice = 16;

        @SerializedName("tmMovePrice")
        public int tmMovePrice = 16;

        @SerializedName("hmMovePrice")
        public int legacyMovePrice = 32;

        @SerializedName("tutorMovePrice")
        public int tutorMovePrice = 16;

        @SerializedName("specialMovePrice")
        public int specialMovePrice = 16;

        @SerializedName("eggMovePrice")
        public int eggMovePrice = 32;
    }

    public static class TutorConfig {
        @SerializedName("hideAlreadyKnownMoves")
        public boolean hideAlreadyKnownMoves = false;

        @SerializedName("levelMove")
        public boolean levelMove = false;

        @SerializedName("tmMove")
        public boolean tmMove = false;

        @SerializedName("hmMove")
        public boolean legacyMove = false;

        @SerializedName("specialMove")
        public boolean specialMove = false;

        @SerializedName("eggMove")
        public boolean eggMove = false;
    }

    // TODO
    @SerializedName("permissionlevels")
    public PermissionLevels permissionLevels = new PermissionLevels();
    public static class PermissionLevels {
        @SerializedName("command.example")
        public int COMMAND_EXAMPLE_PERMISSION_LEVEL =
                Constants.PERMISSION_LEVELS.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();

        @SerializedName("command.example-cool")
        public int COMMAND_EXAMPLE_COOL_PERMISSION_LEVEL =
                Constants.PERMISSION_LEVELS.CHEAT_COMMANDS_AND_COMMAND_BLOCKS.getLevel();
    }
}
