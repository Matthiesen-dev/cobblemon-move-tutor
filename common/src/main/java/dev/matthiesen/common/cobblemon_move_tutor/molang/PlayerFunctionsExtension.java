package dev.matthiesen.common.cobblemon_move_tutor.molang;

import com.bedrockk.molang.runtime.MoParams;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.molang.MoLangFunctions;
import com.cobblemon.mod.common.api.storage.party.PartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.function.Function;

public class PlayerFunctionsExtension {
    public static void register() {
        Constants.createInfoLog("Registering Cobblemon Molang Player function extensions");
        MoLangFunctions.INSTANCE.getPlayerFunctions().add(player -> {
            HashMap<String, Function<MoParams, Object>> map = new HashMap<>();

            // q.player.open_village_tutor(<slot 0-5>)
            map.put("open_village_tutor", params -> {
                sharedTutorFn(params, ((ServerPlayer) player), "village");
                return 0;
            });

            // q.player.open_admin_tutor(<slot 0-5>)
            map.put("open_admin_tutor", params -> {
                sharedTutorFn(params, ((ServerPlayer) player), "admin");
                return 0;
            });

            return map;
        });
    }

    public static void sharedTutorFn(MoParams params, ServerPlayer player, String type) {
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
        PartyStore storage = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = storage.get(slot);
        SelectMoveMenu.openFor(player, pokemon, type);
    }
}
