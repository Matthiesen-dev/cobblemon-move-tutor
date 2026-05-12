package dev.matthiesen.common.cobblemon_move_tutor;

import com.mojang.brigadier.CommandDispatcher;
import dev.matthiesen.common.cobblemon_move_tutor.commands.MoveTutorCMD;
import dev.matthiesen.common.cobblemon_move_tutor.molang.PlayerFunctionsExtension;
import dev.matthiesen.common.cobblemon_move_tutor.providers.*;
import dev.matthiesen.common.cobblemon_move_tutor.platform.*;
import dev.matthiesen.common.cobblemon_move_tutor.registry.*;
import dev.matthiesen.common.cobblemon_move_tutor.config.*;
import dev.matthiesen.common.cobblemon_move_tutor.permissions.ModPermissions;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.ServiceLoader;

public class CobblemonMoveTutorCommon {
    public static final CommonPlatform COMMON_PLATFORM =
            ServiceLoader.load(CommonPlatform.class).findFirst().orElseThrow();
    public static final CurrencyProviderRegistry currencyProviderRegistry =
            new CurrencyProviderRegistry();

    public static final ConfigManager<CommonConfig> COMMON_CONFIG_MANAGER =
            new ConfigManager<>("common", CommonConfig.class);
    public static final ConfigManager<CurrencyProvidersConfig> CURRENCY_PROVIDERS_CONFIG_MANGER =
            new ConfigManager<>("currency_providers", CurrencyProvidersConfig.class);
    public static final ConfigManager<PermissionsConfig> PERMISSIONS_CONFIG_MANAGER =
            new ConfigManager<>("permissions", PermissionsConfig.class);

    public static CommonConfig getCommonConfig() {
        return COMMON_CONFIG_MANAGER.get();
    }

    public static CurrencyProvidersConfig getCurrencyProvidersConfig() {
        return CURRENCY_PROVIDERS_CONFIG_MANGER.get();
    }

    public static PermissionsConfig getPermissionsConfig() {
        return PERMISSIONS_CONFIG_MANAGER.get();
    }

    public static ModPermissions permissions;

    public static void loadConfig() {
        COMMON_CONFIG_MANAGER.load();
        CURRENCY_PROVIDERS_CONFIG_MANGER.load();
        PERMISSIONS_CONFIG_MANAGER.load();
    }

    public static void initialize() {
        Constants.createInfoLog("Initializing common logic");
        permissions = new ModPermissions();
        ItemRegistry.init();
        MenuTypesRegistry.init();
        loadCurrencyProviders();

        // Extend Cobblemon's Molang functions
        PlayerFunctionsExtension.register();
    }

    private static void loadCurrencyProviders() {
        currencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        if (COMMON_PLATFORM.isModLoaded("cobbledollars")) {
            Constants.createInfoLog("Found Cobbledollars, loading compatibility");
            currencyProviderRegistry.register("cobbledollars", CobbleDollarsCurrencyProvider::new);
        }

        if (COMMON_PLATFORM.isModLoaded("impactor")) {
            Constants.createInfoLog("Found Impactor, loading compatibility");
            currencyProviderRegistry.register("impactor", ImpactorCurrencyProvider::new);
        }
    }

    public static void onStartup() {
        Constants.createInfoLog("Server starting, Setting up");
    }

    public static void onShutdown() {
        Constants.createInfoLog("Server stopping, shutting down");
        COMMON_CONFIG_MANAGER.save();
        CURRENCY_PROVIDERS_CONFIG_MANGER.save();
        PERMISSIONS_CONFIG_MANAGER.save();
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection context) {
        Constants.createInfoLog("Registering Commands");
        new MoveTutorCMD().register(dispatcher, registry, context);
    }
}
