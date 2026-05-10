package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerPokemonSelectionGui extends Abstract9x3Gui {
    public ServerPlayer player;
    public String type;

    public PlayerPokemonSelectionGui(ServerPlayer player, String type) {
        this.player = player;
        this.type = type;
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();
        PlayerPartyStore playerParty = Cobblemon.INSTANCE.getStorage().getParty(player);
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = playerParty.get(i);
            if (pokemon != null) {
                buttons.add(
                        GooeyButton.builder()
                                .display(PokemonUtility.pokemonToItem(pokemon))
                                .onClick(action -> {
                                    ServerPlayer sender = action.getPlayer();
                                    new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sender);
                                    new SelectMoveGui(player, pokemon, type).open();
                                })
                                .build()
                );
            } else {
                buttons.add(PokemonUtility.getEmptyPokemonButton());
            }
        }
        return buttons;
    }

    @Override
    public String getTitle() {
        return "Select a Pokemon";
    }
}
