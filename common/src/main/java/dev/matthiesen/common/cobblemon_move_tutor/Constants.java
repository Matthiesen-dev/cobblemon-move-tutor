package dev.matthiesen.common.cobblemon_move_tutor;

import dev.matthiesen.libs.faststats.Token;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Constants {
    public static final String MOD_ID = "cobblemon_move_tutor";
    public static final String ModName = "Cobblemon Move Tutor";
    public static @Token final String METRICS_TOKEN = "0176e95a7a6eb5ab00d5095890ca1d7d";

    public static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Logger LOGGER = LogManager.getLogger(ModName);

    public static void createInfoLog(String message) {
        LOGGER.info(message);
    }

    public static void createErrorLog(String message) {
        LOGGER.error(message);
    }

    public static void createErrorLog(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}
