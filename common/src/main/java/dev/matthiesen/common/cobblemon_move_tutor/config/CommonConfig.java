package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class CommonConfig {
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

        @SerializedName("tutorMove")
        public boolean tutorMove = true;

        @SerializedName("specialMove")
        public boolean specialMove = false;

        @SerializedName("eggMove")
        public boolean eggMove = false;
    }

    @SuppressWarnings("unused")
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
}
