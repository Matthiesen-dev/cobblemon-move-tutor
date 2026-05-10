package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.util.MoveManager;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class SelectMoveGui extends Abstract9x6Gui {
    public ServerPlayer player;
    public Pokemon selectedPokemon;
    private final List<MoveTemplate> moves;
    private final String type;

    public SelectMoveGui(ServerPlayer player, Pokemon selectedPokemon, String type) {
        this.player = player;
        this.selectedPokemon = selectedPokemon;
        this.moves = getFilteredAndSearchMoves();
        this.type = type;
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        for (MoveTemplate move : moves) {
            buttons.add(
                    GooeyButton.builder()
                            .display(PokemonUtility.getMoveItem(move, this.selectedPokemon))
                            .onClick(action -> {
                                ServerPlayer sender = action.getPlayer();
                                new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sender);
                                new ConfirmationGui(sender, Component.translatable("cobblemon_move_tutor.gui.confirmTeach"),
                                        () -> MoveManager.learnMove(sender, selectedPokemon, move, this::open),
                                        this::open, PokemonUtility.getDetailsButton(move, this.selectedPokemon)).open();
                            })
                            .build()
            );
        }
        return buttons;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("cobblemon_move_tutor.gui.selectMoveTitle");
    }

    private CommonConfig.TutorConfig getTutorConfig() {
        if (this.type.equals("admin")) {
            return CobblemonMoveTutorCommon.getCommonConfig().adminTutorConfig;
        }
        return CobblemonMoveTutorCommon.getCommonConfig().villageTutorConfig;
    }

    private List<MoveTemplate> getFilteredAndSearchMoves() {
        return new ArrayList<>(PokemonUtility.getFilteredMoves(this.selectedPokemon, getTutorConfig()));
    }
}
