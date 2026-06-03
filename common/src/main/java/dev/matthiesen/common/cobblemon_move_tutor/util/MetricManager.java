package dev.matthiesen.common.cobblemon_move_tutor.util;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib_api.core.MatthiesenLibApiMetricsManager;
import dev.matthiesen.common.matthiesen_lib_api.core.metric.UniversalMetricContext;
import dev.matthiesen.libs.faststats.ErrorTracker;
import dev.matthiesen.libs.faststats.Metrics;

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
}
