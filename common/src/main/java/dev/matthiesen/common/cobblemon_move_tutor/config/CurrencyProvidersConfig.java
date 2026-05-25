package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class CurrencyProvidersConfig {
    @SerializedName("cobbleDollarsProvider")
    public CobbleDollarsProvider cobbleDollarsProvider = new CobbleDollarsProvider();

    @SerializedName("impactorProvider")
    public ImpactorProvider impactorProvider = new ImpactorProvider();

    @SerializedName("itemProvider")
    public ItemProvider itemProvider = new ItemProvider();

    public static class CobbleDollarsProvider {
        @SerializedName("currencyDisplayName")
        public String currencyDisplayName = "CobbleDollars";
    }

    public static class ImpactorProvider {
        @SerializedName("currencyDisplayName")
        public String currencyDisplayName = "Dollars";

        @SerializedName("currency")
        public String impactorCurrency = "impactor:dollars";
    }

    public static class ItemProvider {
        @SerializedName("currencyDisplayName")
        public String currencyDisplayName = "Rare Candy";

        @SerializedName("itemId")
        public String itemId = "cobblemon:rare_candy";
    }

    @SuppressWarnings("unused")
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
}
