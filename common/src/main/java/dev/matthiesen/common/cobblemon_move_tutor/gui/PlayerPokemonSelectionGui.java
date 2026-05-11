package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.ModelData;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.network.chat.Component;
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

    public Button getTitleButton() {
        return GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                                .setModelData(ModelData.GUI_TEXT.CHOOSE_POKEMON)
                                .hideAdditional()
                                .setCustomName(Component.literal(" "))
                                .build()
                )
                .build();
    }

    @Override
    public ChestTemplate getTemplate() {
        return ChestTemplate.builder(3)
                .set(0, 4, getTitleButton())
                .set(1, 1, getPlaceholder())
                .set(1, 2, getPlaceholder())
                .set(1, 3, getPlaceholder())
                .set(1, 5, getPlaceholder())
                .set(1, 6, getPlaceholder())
                .set(1, 7, getPlaceholder())
                .build();
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
        return "§f\uF8D4\uF8D4\uF8D4\uF8D4\uF8D4\uF8D4\uF8D4\uF8D4\uF8D5";
    }
}
