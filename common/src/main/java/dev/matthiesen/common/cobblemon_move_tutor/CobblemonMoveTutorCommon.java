package dev.matthiesen.common.cobblemon_move_tutor;

import com.mojang.brigadier.CommandDispatcher;
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
    public static final ConfigManager<CommonConfig> COMMON_CONFIG_MANAGER =
            new ConfigManager<>(CommonConfig.class, "config");
    public static final CommonPlatform COMMON_PLATFORM =
            ServiceLoader.load(CommonPlatform.class).findFirst().orElseThrow();
    public static final CurrencyProviderRegistry currencyProviderRegistry =
            new CurrencyProviderRegistry();

    public static ModPermissions permissions;
    public static CommonConfig config;

    public static void loadConfig() {
        config = COMMON_CONFIG_MANAGER.loadConfig();
    }

    public static void initialize() {
        Constants.createInfoLog("Initializing common logic");
        permissions = new ModPermissions();
        SoundRegistry.init();
        StatsRegistry.init();
        BlockRegistry.init();
        BlockEntityRegistry.init();
        ItemRegistry.init();
        CriterionTriggerRegistry.init();
        loadCurrencyProviders();
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
        COMMON_CONFIG_MANAGER.saveConfig();
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection context) {
        Constants.createInfoLog("Registering Commands");
        CommandRegistry.init(dispatcher, registry, context);
    }
}
