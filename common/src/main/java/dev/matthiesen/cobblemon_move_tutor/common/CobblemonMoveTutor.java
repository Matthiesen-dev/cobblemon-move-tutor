package dev.matthiesen.cobblemon_move_tutor.common;

import dev.matthiesen.cobblemon_move_tutor.common.config.CommonConfig;
import dev.matthiesen.cobblemon_move_tutor.common.config.CurrencyProvidersConfig;
import dev.matthiesen.cobblemon_move_tutor.common.config.MoveTutorConfigManager;
import dev.matthiesen.cobblemon_move_tutor.common.config.PermissionsConfig;
import dev.matthiesen.cobblemon_move_tutor.common.molang.PlayerFunctionsExtension;
import dev.matthiesen.cobblemon_move_tutor.common.providers.CobbleDollarsCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.providers.ImpactorCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.providers.ItemCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.registry.*;
import dev.matthiesen.libs.faststats.Token;
import dev.matthiesen.matthiesen_core.common.AbstractCommonMod;
import dev.matthiesen.matthiesen_core.common.api.events.PlatformEvents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class CobblemonMoveTutor extends AbstractCommonMod {
    public static final String MOD_ID = "cobblemon_move_tutor";
    public static final String MOD_NAME = "Cobblemon Move Tutor";
    public static @Token final String METRICS_TOKEN = "0176e95a7a6eb5ab00d5095890ca1d7d";

    public static final CobblemonMoveTutor INSTANCE = new CobblemonMoveTutor();
    private static final CurrencyProviderRegistry currencyProviderRegistry =
            new CurrencyProviderRegistry();

    public static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public CobblemonMoveTutor() {
        super(MOD_ID, MOD_NAME);
    }

    @Override
    public void initialize() {
        super.initialize();
        loadConfig();

        PermissionRegistry.init();
        ItemRegistry.init();
        MenuTypesRegistry.init();
        CommandRegistry.init();

        PlayerFunctionsExtension.register();
        loadCurrencyProviders();

        PlatformEvents.SERVER_RELOAD.subscribe(event -> {
            loadConfig();
            createInfoLog("Reloaded Cobblemon Move Tutor configuration");
        });

        createInfoLog("Initialized Cobblemon Move Tutor");
    }

    @Override
    public @NotNull @Token String getMetricsToken() {
        return METRICS_TOKEN;
    }

    public void loadConfig() {
        MoveTutorConfigManager.getCommonConfigManager().loadConfig();
        MoveTutorConfigManager.getCurrencyProvidersConfigManager().loadConfig();
        MoveTutorConfigManager.getPermissionsConfigManager().loadConfig();
    }

    private void loadCurrencyProviders() {
        currencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        if (getCommonUtils().isModLoaded("cobbledollars")) {
            createInfoLog("Found Cobbledollars, loading compatibility");
            currencyProviderRegistry.register("cobbledollars", CobbleDollarsCurrencyProvider::new);
        }

        if (getCommonUtils().isModLoaded("impactor")) {
            createInfoLog("Found Impactor, loading compatibility");
            currencyProviderRegistry.register("impactor", ImpactorCurrencyProvider::new);
        }
    }

    public CurrencyProviderRegistry getCurrencyProviderRegistry() {
        return currencyProviderRegistry;
    }

    public CommonConfig getCommonConfig() {
        return MoveTutorConfigManager.getCommonConfigManager().getConfig();
    }

    public CurrencyProvidersConfig getCurrencyProvidersConfig() {
        return MoveTutorConfigManager.getCurrencyProvidersConfigManager().getConfig();
    }

    public PermissionsConfig getPermissionsConfig() {
        return MoveTutorConfigManager.getPermissionsConfigManager().getConfig();
    }

    public PermissionRegistry.Permissions getPermissions() {
        return PermissionRegistry.getPermissions();
    }
}
