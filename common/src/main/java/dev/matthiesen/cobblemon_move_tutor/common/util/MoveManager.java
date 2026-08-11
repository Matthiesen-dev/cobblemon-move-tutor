package dev.matthiesen.cobblemon_move_tutor.common.util;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.MoveTutorConfig;
import dev.matthiesen.cobblemon_move_tutor.common.platform.ICurrencyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class MoveManager {
    public static boolean validatePokemon(Pokemon oldPokemon, ServerPlayer player) {
        try {
            if (Cobblemon.INSTANCE.getStorage().getParty(player).get(oldPokemon.getUuid()) == null) {
                player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.msg.unknownPokemon"));
                player.closeContainer();
                return false;
            }

            return true;
        } catch (RuntimeException e) {
            player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.msg.unknownPokemon"));
            player.closeContainer();
            return false;
        }
    }

    public static void learnMove(ServerPlayer player, Pokemon pokemon, MoveTemplate move, Runnable returnToSelect) {
        try {
            var serverConfig = MoveTutorConfig.SERVER_CONFIG;

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

            ICurrencyProvider currencyProvider = CobblemonMoveTutor.INSTANCE.getCurrencyProviderRegistry().get(serverConfig.currency_type.get());

            if (currencyProvider == null) {
                player.sendSystemMessage(Component.translatable("cobblemon_move_tutor.msg.invalidCurrency", serverConfig.currency_type.get()));
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
        } catch (RuntimeException e) {
            CobblemonMoveTutor.INSTANCE.createErrorLog("learnMove", e);
            player.sendSystemMessage(Component.translatable(
                    "cobblemon_move_tutor.msg.learnMoveFailed",
                    move.getDisplayName().getString(),
                    pokemon.getDisplayName(false)
            ));
            returnToSelect.run();
        }
    }
}
