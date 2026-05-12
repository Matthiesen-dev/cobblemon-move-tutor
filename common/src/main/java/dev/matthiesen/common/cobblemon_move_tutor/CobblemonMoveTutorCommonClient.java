package dev.matthiesen.common.cobblemon_move_tutor;

import dev.matthiesen.common.cobblemon_move_tutor.platform.CommonClientPlatform;

import java.util.ServiceLoader;

public class CobblemonMoveTutorCommonClient {
    public static final CommonClientPlatform COMMON_CLIENT_PLATFORM =
            ServiceLoader.load(CommonClientPlatform.class).findFirst().orElseThrow();

    public static void initialize() {
        Constants.createInfoLog("Initializing client logic");
    }

    public static void registerMenuScreens(CommonClientPlatform.ScreenRegistrar registrar) {
        COMMON_CLIENT_PLATFORM.registerMenuScreens(registrar);
    }
}
