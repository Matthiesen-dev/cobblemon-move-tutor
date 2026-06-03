package dev.matthiesen.common.cobblemon_move_tutor.util;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib_api.core.MatthiesenLibApiMetricsManager;
import dev.matthiesen.common.matthiesen_lib_api.core.interfaces.MatthiesenLibServerEventHandler;
import dev.matthiesen.common.matthiesen_lib_api.core.metric.UniversalMetricContext;
import dev.matthiesen.libs.faststats.ErrorTracker;
import dev.matthiesen.libs.faststats.Metrics;
import net.minecraft.server.MinecraftServer;

public final class MetricManager {
    public static final ErrorTracker ERROR_TRACKER = MatthiesenLibApiMetricsManager.getErrorTracker();
    private static final UniversalMetricContext metricContext = new UniversalMetricContext.Factory(
            Constants.MOD_ID,
            Constants.METRICS_TOKEN
    )
            .metrics(Metrics.Factory::create)
            .errorTrackerService(ERROR_TRACKER)
            .create();

    public static UniversalMetricContext getMetricContext() {
        return metricContext;
    }

    public static MatthiesenLibServerEventHandler getServerEventHandler() {
        return new MatthiesenLibServerEventHandler() {
            @Override
            public void onServerStart(MinecraftServer server) {
                getMetricContext().ready();
            }

            @Override
            public void onServerStop(MinecraftServer server) {
                getMetricContext().shutdown();
            }
        };
    }
}
