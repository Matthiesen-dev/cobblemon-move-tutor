package dev.matthiesen.common.cobblemon_move_tutor.util;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.architectury.registry.menu.MenuRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.PokemonSelectionMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;

public class TutorMenuProvider {
    public static SimpleMenuProvider create(MenuConstructor constructor) {
        return new SimpleMenuProvider(constructor, Component.empty());
    }

    public static class open {
        public static void confirmationMenu(ServerPlayer player, ItemStack detailsItem,
                                            Runnable confirmCallback, Runnable cancelCallback) {
            MenuRegistry.openMenu(player, TutorMenuProvider.create((id, inv, p) ->
                    new ConfirmationMenu(id, inv, detailsItem, confirmCallback, cancelCallback)));
        }

        public static void pokemonSelectionMenu(ServerPlayer player, String type) {
            PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon[] party = new Pokemon[6];
            for (int i = 0; i < 6; i++) party[i] = partyStore.get(i);
            MenuRegistry.openMenu(player, TutorMenuProvider.create((id, inv, p) ->
                    new PokemonSelectionMenu(id, inv, party, type)));
        }

        public static void selectMoveMenu(ServerPlayer player, Pokemon pokemon, String type) {
            MenuRegistry.openMenu(player, TutorMenuProvider.create((id, inv, p) ->
                    new SelectMoveMenu(id, inv, player, pokemon, type)));
        }
    }
}
