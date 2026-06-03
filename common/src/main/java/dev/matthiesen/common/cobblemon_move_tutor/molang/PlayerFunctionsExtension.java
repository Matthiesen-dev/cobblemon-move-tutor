package dev.matthiesen.common.cobblemon_move_tutor.molang;

import com.bedrockk.molang.runtime.MoParams;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.molang.MoLangFunctions;
import com.cobblemon.mod.common.api.storage.party.PartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.util.MetricManager;
import dev.matthiesen.common.cobblemon_move_tutor.util.TutorMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.function.Function;

public final class PlayerFunctionsExtension {
    public static void register() {
        Constants.createInfoLog("Registering Cobblemon Molang Player function extensions");
        MoLangFunctions.INSTANCE.getPlayerFunctions().add(player -> {
            HashMap<String, Function<MoParams, Object>> map = new HashMap<>();

            // q.player.open_village_tutor(<slot 0-5>)
            map.put("open_village_tutor", params -> {
                sharedTutorFn(params, player, "village");
                return 0;
            });

            // q.player.open_admin_tutor(<slot 0-5>)
            map.put("open_admin_tutor", params -> {
                sharedTutorFn(params, player, "admin");
                return 0;
            });

            return map;
        });
    }

    public static void sharedTutorFn(MoParams params, Player player, String type) {
        try {
            int slot = params.getInt(0);
            if (slot < 0 || slot >= 6) {
                Constants.createErrorLog(
                        "Invalid slot index %slot% for player %player% in open_%type%_tutor MoLang function"
                                .replaceAll("%slot%", String.valueOf(slot))
                                .replaceAll("%player%", player.getName().getString())
                                .replaceAll("%type%", type)
                );
                return;
            }
            if (!(player instanceof ServerPlayer serverPlayer)) {
                Constants.createErrorLog(
                        "Player %player% is not a ServerPlayer and cannot open tutor menu"
                                .replaceAll("%player%", player.getName().getString())
                );
                return;
            }
            PartyStore storage = Cobblemon.INSTANCE.getStorage().getParty(serverPlayer);
            Pokemon pokemon = storage.get(slot);
            TutorMenuProvider.open.selectMoveMenu(serverPlayer, pokemon, type);
        } catch (RuntimeException e) {
            MetricManager.ERROR_TRACKER.trackError(e);
            Constants.createErrorLog(
                    "Exception while executing open_%type%_tutor MoLang function for player %player%"
                            .replaceAll("%type%", type)
                            .replaceAll("%player%", player.getName().getString()),
                    e
            );
        }
    }
}
