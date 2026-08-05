package dev.matthiesen.cobblemon_move_tutor.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CommonServerConfig {

    // Currency Provider Config
    public ModConfigSpec.ConfigValue<String> currencyProvider_cobbleDollars_displayName;
    public ModConfigSpec.ConfigValue<String> currencyProvider_impactor_displayName;
    public ModConfigSpec.ConfigValue<String> currencyProvider_impactor_currency;
    public ModConfigSpec.ConfigValue<String> currencyProvider_item_displayName;
    public ModConfigSpec.ConfigValue<String> currencyProvider_item_itemId;

    // Currency Config
    public ModConfigSpec.ConfigValue<String> currency_type;
    public ModConfigSpec.IntValue currency_levelMovePrice;
    public ModConfigSpec.IntValue currency_tmMovePrice;
    public ModConfigSpec.IntValue currency_legacyMovePrice;
    public ModConfigSpec.IntValue currency_tutorMovePrice;
    public ModConfigSpec.IntValue currency_specialMovePrice;
    public ModConfigSpec.IntValue currency_eggMovePrice;

    // Village Tutor Config
    public ModConfigSpec.BooleanValue village_hideAlreadyKnownMoves;
    public ModConfigSpec.BooleanValue village_levelMove;
    public ModConfigSpec.BooleanValue village_tmMove;
    public ModConfigSpec.BooleanValue village_legacyMove;
    public ModConfigSpec.BooleanValue village_tutorMove;
    public ModConfigSpec.BooleanValue village_specialMove;
    public ModConfigSpec.BooleanValue village_eggMove;

    // Admin Tutor Config
    public ModConfigSpec.BooleanValue admin_hideAlreadyKnownMoves;
    public ModConfigSpec.BooleanValue admin_levelMove;
    public ModConfigSpec.BooleanValue admin_tmMove;
    public ModConfigSpec.BooleanValue admin_legacyMove;
    public ModConfigSpec.BooleanValue admin_tutorMove;
    public ModConfigSpec.BooleanValue admin_specialMove;
    public ModConfigSpec.BooleanValue admin_eggMove;

    public CommonServerConfig(ModConfigSpec.Builder builder) {
        builder.comment("Currency Provider Config").push("currencyProviderConfig");
        currencyProvider_cobbleDollars_displayName = builder.comment("Display name for CobbleDollars currency provider.")
                .define("cobbleDollarsProvider_displayName", "CobbleDollars");
        currencyProvider_impactor_displayName = builder.comment("Display name for Impactor currency provider.")
                .define("impactorProvider_displayName", "Dollars");
        currencyProvider_impactor_currency = builder.comment("Currency identifier for Impactor currency provider.")
                .define("impactorProvider_currency", "impactor:dollars");
        currencyProvider_item_displayName = builder.comment("Display name for Item currency provider.")
                .define("itemProvider_displayName", "Rare Candy");
        currencyProvider_item_itemId = builder.comment("Item ID for Item currency provider.")
                .define("itemProvider_itemId", "cobblemon:rare_candy");
        builder.pop();

        builder.comment("Currency Config").push("currencyConfig");
        currency_type = builder.comment("Type of currency used for move tutoring. Options: 'item', 'cobbledollars', 'impactor'.")
                .define("currencyType", "item");
        currency_levelMovePrice = builder.comment("Price for level-up moves.")
                .defineInRange("levelMovePrice", 16, 0, Integer.MAX_VALUE);
        currency_tmMovePrice = builder.comment("Price for TM moves.")
                .defineInRange("tmMovePrice", 16, 0, Integer.MAX_VALUE);
        currency_legacyMovePrice = builder.comment("Price for legacy moves (HMs).")
                .defineInRange("legacyMovePrice", 32, 0, Integer.MAX_VALUE);
        currency_tutorMovePrice = builder.comment("Price for tutor moves.")
                .defineInRange("tutorMovePrice", 16, 0, Integer.MAX_VALUE);
        currency_specialMovePrice = builder.comment("Price for special moves.")
                .defineInRange("specialMovePrice", 16, 0, Integer.MAX_VALUE);
        currency_eggMovePrice = builder.comment("Price for egg moves.")
                .defineInRange("eggMovePrice", 32, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.comment("Village Tutor Config").push("villageTutorConfig");
        village_hideAlreadyKnownMoves = builder.comment("Whether to hide moves that the Pokémon already knows in the village tutor interface.")
                .define("hideAlreadyKnownMoves", false);
        village_levelMove = builder.comment("Whether level-up moves are available in the village tutor.")
                .define("levelMove", false);
        village_tmMove = builder.comment("Whether TM moves are available in the village tutor.")
                .define("tmMove", false);
        village_legacyMove = builder.comment("Whether legacy moves (HMs) are available in the village tutor.")
                .define("legacyMove", true);
        village_tutorMove = builder.comment("Whether tutor moves are available in the village tutor.")
                .define("tutorMove", true);
        village_specialMove = builder.comment("Whether special moves are available in the village tutor.")
                .define("specialMove", true);
        village_eggMove = builder.comment("Whether egg moves are available in the village tutor.")
                .define("eggMove", false);
        builder.pop();

        builder.comment("Admin Tutor Config").push("adminTutorConfig");
        admin_hideAlreadyKnownMoves = builder.comment("Whether to hide moves that the Pokémon already knows in the admin tutor interface.")
                .define("hideAlreadyKnownMoves", false);
        admin_levelMove = builder.comment("Whether level-up moves are available in the admin tutor.")
                .define("levelMove", false);
        admin_tmMove = builder.comment("Whether TM moves are available in the admin tutor.")
                .define("tmMove", false);
        admin_legacyMove = builder.comment("Whether legacy moves (HMs) are available in the admin tutor.")
                .define("legacyMove", true);
        admin_tutorMove = builder.comment("Whether tutor moves are available in the admin tutor.")
                .define("tutorMove", true);
        admin_specialMove = builder.comment("Whether special moves are available in the admin tutor.")
                .define("specialMove", true);
        admin_eggMove = builder.comment("Whether egg moves are available in the admin tutor.")
                .define("eggMove", false);
        builder.pop();
    }
}
