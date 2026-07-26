package dev.matthiesen.cobblemon_move_tutor.common.config;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.matthiesen_core.common.utility.config.ConfigManager;

public final class MoveTutorConfigManager {
    private static <T> ConfigManager<T> createConfigManager(Class<T> configClass, String configName) {
        return new ConfigManager<>(configClass, configName, CobblemonMoveTutor.MOD_ID);
    }

    private static final ConfigManager<CommonConfig> COMMON_CONFIG_MANAGER =
            createConfigManager(CommonConfig.class, "common");
    private static final ConfigManager<CurrencyProvidersConfig> CURRENCY_PROVIDERS_CONFIG_MANAGER =
            createConfigManager(CurrencyProvidersConfig.class, "currency_providers");
    private static final ConfigManager<PermissionsConfig> PERMISSIONS_CONFIG_MANAGER =
            createConfigManager(PermissionsConfig.class, "permissions");

    public static ConfigManager<CommonConfig> getCommonConfigManager() {
        return COMMON_CONFIG_MANAGER;
    }

    public static ConfigManager<CurrencyProvidersConfig> getCurrencyProvidersConfigManager() {
        return CURRENCY_PROVIDERS_CONFIG_MANAGER;
    }

    public static ConfigManager<PermissionsConfig> getPermissionsConfigManager() {
        return PERMISSIONS_CONFIG_MANAGER;
    }
}
