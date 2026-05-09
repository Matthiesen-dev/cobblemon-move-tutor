package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class GuiConfig {
    @SerializedName("frameItemId")
    public String frameItemId = "minecraft:gray_stained_glass_pane";

    @SerializedName("emptyPokemonId")
    public String emptyPokemonId = "cobblemon:poke_ball";

    @SerializedName("currentPageItemId")
    public String currentPageItemId = "minecraft:paper";

    @SerializedName("navigationItemId")
    public String navigationItemId = "minecraft:arrow";

    @SuppressWarnings("unused")
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
}
