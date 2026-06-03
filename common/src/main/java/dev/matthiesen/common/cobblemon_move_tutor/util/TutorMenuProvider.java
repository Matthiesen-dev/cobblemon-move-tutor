package dev.matthiesen.common.cobblemon_move_tutor.util;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.PokemonSelectionMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import dev.matthiesen.common.matthiesen_lib.utility.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;

public final class TutorMenuProvider extends MenuProvider {
    public static SimpleMenuProvider createProvider(MenuConstructor constructor) {
        return new SimpleMenuProvider(constructor, Component.empty());
    }

    public static class open {
        public static void confirmationMenu(ServerPlayer player, ItemStack detailsItem,
                                            Runnable confirmCallback, Runnable cancelCallback) {
            try {
                TutorMenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new ConfirmationMenu(id, inv, detailsItem, confirmCallback, cancelCallback)));
            } catch (RuntimeException e) {
                MetricManager.ERROR_TRACKER.trackError(e);
                Constants.createErrorLog("confirmationMenu", e);
            }
        }

        public static void pokemonSelectionMenu(ServerPlayer player, String type) {
            try {
                PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
                Pokemon[] party = new Pokemon[6];
                for (int i = 0; i < 6; i++) party[i] = partyStore.get(i);
                TutorMenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new PokemonSelectionMenu(id, inv, party, type)));
            } catch (RuntimeException e) {
                MetricManager.ERROR_TRACKER.trackError(e);
                Constants.createErrorLog("pokemonSelectionMenu", e);
            }
        }

        public static void selectMoveMenu(ServerPlayer player, Pokemon pokemon, String type) {
            try {
                TutorMenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new SelectMoveMenu(id, inv, player, pokemon, type)));
            } catch (RuntimeException e) {
                MetricManager.ERROR_TRACKER.trackError(e);
                Constants.createErrorLog("selectMoveMenu", e);
            }
        }
    }
}
