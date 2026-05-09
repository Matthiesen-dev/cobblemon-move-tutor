package dev.matthiesen.common.cobblemon_move_tutor.gui.menus;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class PlayerPokemonSelectionGui extends Abstract9x3Gui {
    public ServerPlayer player;

    public PlayerPokemonSelectionGui(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

    private Button getEmptyPokemonButton() {
        ResourceLocation rsLoc = ResourceLocation.parse(CobblemonMoveTutorCommon.guiConfig.emptyPokemonId);
        Item item = BuiltInRegistries.ITEM.get(rsLoc);
        return GooeyButton.builder()
                .display(
                        new ItemBuilder(item)
                                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.emptySlot")
                                        .withStyle(ChatFormatting.GRAY))
                                .hideAdditional()
                                .build()
                )
                .onClick(action ->
                        new SoundsPlayer(CobblemonSounds.POKE_BALL_HIT)
                                .play(action.getPlayer()))
                .build();
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();
        PlayerPartyStore playerParty = Cobblemon.INSTANCE.getStorage().getParty(player);
        for (int i = 0; i < 5; i++) {
            Pokemon pokemon = playerParty.get(i);
            if (pokemon != null) {
                buttons.add(
                        GooeyButton.builder()
                                .display(PokemonUtility.pokemonToItem(pokemon))
                                .onClick(action -> {
                                    ServerPlayer sender = action.getPlayer();
                                    new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sender);
                                    // TODO
                                })
                                .build()
                );
            } else {
                buttons.add(
                        getEmptyPokemonButton()
                );
            }
        }
        return buttons;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("cobblemon_move_tutor.gui.selectPokemonTitle");
    }
}
