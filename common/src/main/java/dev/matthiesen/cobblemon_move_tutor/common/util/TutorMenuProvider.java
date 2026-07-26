package dev.matthiesen.cobblemon_move_tutor.common.util;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.ui.ConfirmationMenu;
import dev.matthiesen.cobblemon_move_tutor.common.ui.PokemonSelectionMenu;
import dev.matthiesen.cobblemon_move_tutor.common.ui.SelectMoveMenu;
import dev.matthiesen.matthiesen_core.common.utility.ui.menu.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;

public final class TutorMenuProvider {
    public static SimpleMenuProvider createProvider(MenuConstructor constructor) {
        return new SimpleMenuProvider(constructor, Component.empty());
    }

    public static class open {
        public static void confirmationMenu(ServerPlayer player, ItemStack detailsItem,
                                            Runnable confirmCallback, Runnable cancelCallback) {
            try {
                MenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new ConfirmationMenu(id, inv, detailsItem, confirmCallback, cancelCallback)));
            } catch (RuntimeException e) {
                CobblemonMoveTutor.INSTANCE.createErrorLog("confirmationMenu", e);
            }
        }

        public static void pokemonSelectionMenu(ServerPlayer player, String type) {
            try {
                PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
                Pokemon[] party = new Pokemon[6];
                for (int i = 0; i < 6; i++) party[i] = partyStore.get(i);
                MenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new PokemonSelectionMenu(id, inv, party, type)));
            } catch (RuntimeException e) {
                CobblemonMoveTutor.INSTANCE.createErrorLog("pokemonSelectionMenu", e);
            }
        }

        public static void selectMoveMenu(ServerPlayer player, Pokemon pokemon, String type) {
            try {
                MenuProvider.openMenu(player, TutorMenuProvider.createProvider((id, inv, p) ->
                        new SelectMoveMenu(id, inv, player, pokemon, type)));
            } catch (RuntimeException e) {
                CobblemonMoveTutor.INSTANCE.createErrorLog("selectMoveMenu", e);
            }
        }
    }
}
