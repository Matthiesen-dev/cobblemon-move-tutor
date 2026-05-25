package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.platform.ICurrencyProvider;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class CurrencyProviderRegistry {
    @NotNull
    private final Map<String, Supplier<ICurrencyProvider>> PROVIDERS = new HashMap<>();

    public void register(@NotNull String key, @NotNull Supplier<ICurrencyProvider> supplier) {
        PROVIDERS.put(key.toLowerCase(Locale.ROOT), supplier);
    }

    public ICurrencyProvider get(@NotNull String key) {
        return PROVIDERS.getOrDefault(key.toLowerCase(Locale.ROOT), () -> null).get();
    }
}
