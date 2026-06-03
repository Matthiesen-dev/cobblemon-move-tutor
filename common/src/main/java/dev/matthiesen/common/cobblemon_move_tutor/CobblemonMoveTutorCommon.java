package dev.matthiesen.common.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.molang.PlayerFunctionsExtension;
import dev.matthiesen.common.cobblemon_move_tutor.providers.*;
import dev.matthiesen.common.cobblemon_move_tutor.platform.*;
import dev.matthiesen.common.cobblemon_move_tutor.registry.*;
import dev.matthiesen.common.cobblemon_move_tutor.config.*;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
import dev.matthiesen.common.matthiesen_lib_api.MatthiesenLibApi;
import dev.matthiesen.common.matthiesen_lib_api.config.ConfigManager;

public class CobblemonMoveTutorCommon {
    public static final CurrencyProviderRegistry currencyProviderRegistry =
            new CurrencyProviderRegistry();

    private static final ConfigManager<CommonConfig> COMMON_CONFIG_MANAGER =
            new ConfigManager<>(CommonConfig.class, "common", Constants.MOD_ID);
    private static final ConfigManager<CurrencyProvidersConfig> CURRENCY_PROVIDERS_CONFIG_MANGER =
            new ConfigManager<>(CurrencyProvidersConfig.class, "currency_providers", Constants.MOD_ID);
    private static final ConfigManager<PermissionsConfig> PERMISSIONS_CONFIG_MANAGER =
            new ConfigManager<>(PermissionsConfig.class, "permissions", Constants.MOD_ID);

    public static CommonConfig getCommonConfig() {
        return COMMON_CONFIG_MANAGER.getConfig();
    }

    public static CurrencyProvidersConfig getCurrencyProvidersConfig() {
        return CURRENCY_PROVIDERS_CONFIG_MANGER.getConfig();
    }

    public static PermissionsConfig getPermissionsConfig() {
        return PERMISSIONS_CONFIG_MANAGER.getConfig();
    }

    public static PermissionRegistry.Permissions getPermissions() {
        return PermissionRegistry.getPermissions();
    }

    public static void loadConfig() {
        COMMON_CONFIG_MANAGER.loadConfig();
        CURRENCY_PROVIDERS_CONFIG_MANGER.loadConfig();
        PERMISSIONS_CONFIG_MANAGER.loadConfig();
    }

    public static void reload() {
        loadConfig();
        Constants.createInfoLog("Reloaded Cobblemon Move Tutor configs");
    }

    public static void initialize() {
        Constants.createInfoLog("Initializing common logic");

        // Initialize registries
        PermissionRegistry.init();
        ItemRegistry.init();
        MenuTypesRegistry.init();
        CommandRegistry.init();

        // Load Currency Providers and populate registry
        loadCurrencyProviders();

        // Extend Cobblemon's Molang functions
        PlayerFunctionsExtension.register();

        MatthiesenLibApi.registerReloadRunnable(Constants.MOD_ID, CobblemonMoveTutorCommon::reload);
        MatthiesenLibApi.registerModToMetrics(Constants.MOD_ID);
    }

    private static void loadCurrencyProviders() {
        currencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        if (MatthiesenLib.isModLoaded("cobbledollars")) {
            Constants.createInfoLog("Found Cobbledollars, loading compatibility");
            currencyProviderRegistry.register("cobbledollars", CobbleDollarsCurrencyProvider::new);
        }

        if (MatthiesenLib.isModLoaded("impactor")) {
            Constants.createInfoLog("Found Impactor, loading compatibility");
            currencyProviderRegistry.register("impactor", ImpactorCurrencyProvider::new);
        }
    }
}
