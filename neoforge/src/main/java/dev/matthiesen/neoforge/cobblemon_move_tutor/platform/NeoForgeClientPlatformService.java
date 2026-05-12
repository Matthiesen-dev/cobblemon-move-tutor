package dev.matthiesen.neoforge.cobblemon_move_tutor.platform;

import dev.matthiesen.common.cobblemon_move_tutor.platform.ScreenRegistrar;
import dev.matthiesen.common.cobblemon_move_tutor.platform.CommonClientPlatform;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuScreenRegistry;

public class NeoForgeClientPlatformService implements CommonClientPlatform {
    @Override
    public void registerMenuScreens(ScreenRegistrar registrar) {
        MenuScreenRegistry.registerAll(registrar);
    }
}
