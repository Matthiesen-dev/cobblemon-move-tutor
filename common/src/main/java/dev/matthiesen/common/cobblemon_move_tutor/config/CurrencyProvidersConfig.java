package dev.matthiesen.common.cobblemon_move_tutor.config;

import com.google.gson.annotations.SerializedName;

public class CurrencyProvidersConfig extends ConfigBase {
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
        public String currencyDisplayName = "Money";

        @SerializedName("currency")
        public String impactorCurrency = "impactor:dollars";
    }

    public static class ItemProvider {
        @SerializedName("currencyDisplayName")
        public String currencyDisplayName = "Rare Candy";

        @SerializedName("itemId")
        public String itemId = "cobblemon:rare_candy";
    }
}
