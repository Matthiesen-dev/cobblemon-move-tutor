package dev.matthiesen.common.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.molang.PlayerFunctionsExtension;
import dev.matthiesen.common.cobblemon_move_tutor.providers.*;
import dev.matthiesen.common.cobblemon_move_tutor.platform.*;
import dev.matthiesen.common.cobblemon_move_tutor.registry.*;
import dev.matthiesen.common.cobblemon_move_tutor.config.*;
import dev.matthiesen.common.cobblemon_move_tutor.util.MetricManager;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
import dev.matthiesen.common.matthiesen_lib_api.MatthiesenLibApi;

public final class CobblemonMoveTutorCommon {
    public static final CurrencyProviderRegistry currencyProviderRegistry =
            new CurrencyProviderRegistry();

    public static void loadConfig() {
        MoveTutorConfigManager.getCommonConfigManager().loadConfig();
        MoveTutorConfigManager.getCurrencyProvidersConfigManager().loadConfig();
        MoveTutorConfigManager.getPermissionsConfigManager().loadConfig();
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
        MatthiesenLibApi.registerServerEventHandler(Constants.MOD_ID, MetricManager.getServerEventHandler());
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

    public static CommonConfig getCommonConfig() {
        return MoveTutorConfigManager.getCommonConfigManager().getConfig();
    }

    public static CurrencyProvidersConfig getCurrencyProvidersConfig() {
        return MoveTutorConfigManager.getCurrencyProvidersConfigManager().getConfig();
    }

    public static PermissionsConfig getPermissionsConfig() {
        return MoveTutorConfigManager.getPermissionsConfigManager().getConfig();
    }

    public static PermissionRegistry.Permissions getPermissions() {
        return PermissionRegistry.getPermissions();
    }
}
