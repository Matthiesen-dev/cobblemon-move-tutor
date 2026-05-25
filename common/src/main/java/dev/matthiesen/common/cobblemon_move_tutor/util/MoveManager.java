package dev.matthiesen.common.cobblemon_move_tutor.util;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.platform.ICurrencyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MoveManager {
    public static boolean validatePokemon(Pokemon oldPokemon, ServerPlayer player) {
        if (Cobblemon.INSTANCE.getStorage().getParty(player).get(oldPokemon.getUuid()) == null) {
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.msg.unknownPokemon"));
            player.closeContainer();
            return false;
        }

        return true;
    }

    public static void learnMove(ServerPlayer player, Pokemon pokemon, MoveTemplate move, Runnable returnToSelect) {
        CommonConfig serverConfig = CobblemonMoveTutorCommon.getCommonConfig();

        if (!validatePokemon(pokemon, player)) {
            return;
        }

        MoveSet moveSet = pokemon.getMoveSet();

        if (PokemonUtility.isLearnedMove(pokemon, move)) {
            player.sendSystemMessage(Component.translatable(
                    "cobblemon_move_tutor.msg.alreadyKnowsMove",
                    pokemon.getDisplayName(false).getString(),
                    move.getDisplayName().getString()
            ));
            returnToSelect.run();
            return;
        }

        ICurrencyProvider currencyProvider = CobblemonMoveTutorCommon.currencyProviderRegistry.get(serverConfig.currencyConfig.currencyType);

        if (currencyProvider == null) {
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.msg.invalidCurrency", serverConfig.currencyConfig.currencyType));
            return;
        }

        int price = PokemonUtility.getMovePrice(pokemon, move);

        if (!currencyProvider.buy(player, pokemon, move, price)) {
            return;
        }

        if (!moveSet.hasSpace()) {
            pokemon.getBenchedMoves().add(new BenchedMove(move, 0));
        } else {
            moveSet.add(move.create());
        }

        returnToSelect.run();
        currencyProvider.successfulBuyMessage(player, pokemon, move, price);
    }
}
