package dev.matthiesen.common.cobblemon_move_tutor.config;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib_api.config.ConfigManager;

public class MoveTutorConfigManager<T> extends ConfigManager<T> {
    public MoveTutorConfigManager(Class<T> configClass, String configName) {
        super(configClass, configName, Constants.MOD_ID);
    }

    private static final ConfigManager<CommonConfig> COMMON_CONFIG_MANAGER =
            new MoveTutorConfigManager<>(CommonConfig.class, "common");
    private static final ConfigManager<CurrencyProvidersConfig> CURRENCY_PROVIDERS_CONFIG_MANGER =
            new MoveTutorConfigManager<>(CurrencyProvidersConfig.class, "currency_providers");
    private static final ConfigManager<PermissionsConfig> PERMISSIONS_CONFIG_MANAGER =
            new MoveTutorConfigManager<>(PermissionsConfig.class, "permissions");

    public static ConfigManager<CommonConfig> getCommonConfigManager() {
        return COMMON_CONFIG_MANAGER;
    }

    public static ConfigManager<CurrencyProvidersConfig> getCurrencyProvidersConfigManager() {
        return CURRENCY_PROVIDERS_CONFIG_MANGER;
    }

    public static ConfigManager<PermissionsConfig> getPermissionsConfigManager() {
        return PERMISSIONS_CONFIG_MANAGER;
    }
}
