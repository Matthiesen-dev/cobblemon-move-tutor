package dev.matthiesen.cobblemon_move_tutor.common;

import dev.matthiesen.cobblemon_move_tutor.common.config.*;
import dev.matthiesen.cobblemon_move_tutor.common.molang.PlayerFunctionsExtension;
import dev.matthiesen.cobblemon_move_tutor.common.providers.CobbleDollarsCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.providers.ImpactorCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.providers.ItemCurrencyProvider;
import dev.matthiesen.cobblemon_move_tutor.common.registry.*;
import dev.matthiesen.libs.faststats.Token;
import dev.matthiesen.matthiesen_core.common.AbstractCommonMod;
import dev.matthiesen.matthiesen_core.common.api.events.PlatformEvents;
import dev.matthiesen.matthiesen_core.common.api.platform.loader.ModConfigType;
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
        registerModConfig(MOD_ID, ModConfigType.SERVER, MoveTutorConfig.SERVER_SPEC, "cobblemon_move_tutor/server.toml");
        registerModConfig(MOD_ID, ModConfigType.STARTUP, MoveTutorConfig.PERMISSIONS_SPEC, "cobblemon_move_tutor/permissions.toml");

        PermissionRegistry.init();
        ItemRegistry.init();
        MenuTypesRegistry.init();
        CommandRegistry.init();

        PlayerFunctionsExtension.register();

        PlatformEvents.SERVER_STARTING.subscribe(event -> {
            loadCurrencyProviders();
            createInfoLog("Loaded server resources");
        });

        createInfoLog("Initialized Cobblemon Move Tutor");
    }

    @Override
    public @NotNull @Token String getMetricsToken() {
        return METRICS_TOKEN;
    }

    private void loadCurrencyProviders() {
        currencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        // TODO: Re-enable this when Cobbledollars is updated to support Cobblemon 1.8.0
//        if (getCommonUtils().isModLoaded("cobbledollars")) {
//            createInfoLog("Found Cobbledollars, loading compatibility");
//            currencyProviderRegistry.register("cobbledollars", CobbleDollarsCurrencyProvider::new);
//        }

        if (getCommonUtils().isModLoaded("impactor")) {
            createInfoLog("Found Impactor, loading compatibility");
            currencyProviderRegistry.register("impactor", ImpactorCurrencyProvider::new);
        }
    }

    public CurrencyProviderRegistry getCurrencyProviderRegistry() {
        return currencyProviderRegistry;
    }

    public PermissionRegistry.Permissions getPermissions() {
        return PermissionRegistry.getPermissions();
    }
}
