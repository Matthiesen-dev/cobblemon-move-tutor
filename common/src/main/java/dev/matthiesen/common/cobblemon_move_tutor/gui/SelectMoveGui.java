package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.MoveManager;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ItemStack getMoveItem(MoveTemplate move) {
        return new ItemBuilder(PokemonUtility.getItemTM(move))
                .setCustomName(move.getDisplayName())
                .addLore(getMoveLore(selectedPokemon, move))
                .build();
    }

    public Button getDetailsButton(MoveTemplate move) {
        return GooeyButton.builder().display(getMoveItem(move)).build();
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        for (MoveTemplate move : moves) {
            buttons.add(
                    GooeyButton.builder()
                            .display(getMoveItem(move))
                            .onClick(action -> {
                                ServerPlayer sender = action.getPlayer();
                                new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sender);
                                new CancelConfirmGuiBuilder(sender, Component.translatable("cobblemon_move_tutor.gui.confirmTeach"),
                                        () -> MoveManager.learnMove(sender, selectedPokemon, move, this::open),
                                        this::open, getDetailsButton(move)).open();
                            })
                            .build()
            );
        }
        return buttons;
    }

    private static String getCategoryIcon(MoveTemplate Move) {
        return switch (Move.getDamageCategory().getName()) {
            case "PHYSICAL" -> " ⚔";
            case "SPECIAL" -> " ⚡";
            default -> " 🧪";
        };
    }

    public static Component[] getMoveLore(Pokemon pokemon, MoveTemplate move) {
        List<Component> lore = new ArrayList<>();

        var type = move.getElementalType().getDisplayName().copy()
                .withColor(move.getElementalType().getHue());
        var typeLine = Component.translatable("cobblemon_move_tutor.gui.typeLore", type)
                .append(getCategoryIcon(move));

        lore.add(typeLine);
        lore.add(Component.translatable("cobblemon_move_tutor.gui.powerLore", PokemonUtility.formatStatMove(move.getPower())));
        lore.add(Component.translatable("cobblemon_move_tutor.gui.accuracyLore", PokemonUtility.formatStatMove(move.getAccuracy())));
        lore.add(Component.translatable("cobblemon_move_tutor.gui.ppLore", String.valueOf(move.getPp()), String.valueOf(move.getMaxPp())));

        var price = PokemonUtility.getMovePrice(pokemon, move);

        if (price > 0) {
            var serverConfig = CobblemonMoveTutorCommon.getCommonConfig();
            var currencyProvider = CobblemonMoveTutorCommon.currencyProviderRegistry.get(serverConfig.currencyConfig.currencyType);

            if (currencyProvider == null) {
                lore.add(Component.translatable("cobblemon_move_tutor.gui.invalidCurrency", serverConfig.currencyConfig.currencyType));
            } else {
                lore.add(currencyProvider.lore(price));
            }
        } else {
            lore.add(Component.translatable("cobblemon_move_tutor.gui.freeLore"));
        }

        return lore.toArray(new Component[0]);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("cobblemon_move_tutor.gui.selectMoveTitle");
    }

    private CommonConfig.TutorConfig getTutorConfig() {
        if (this.type.equals("admin")) {
            return CobblemonMoveTutorCommon.getCommonConfig().adminTutorConfig;
        } else {
            return CobblemonMoveTutorCommon.getCommonConfig().villageTutorConfig;
        }
    }

    private List<MoveTemplate> getFilteredAndSearchMoves() {
        return new ArrayList<>(getFilteredMoves(this.selectedPokemon.getForm().getMoves()));
    }

    private Set<MoveTemplate> getFilteredMoves(Learnset moves) {
        var config = getTutorConfig();
        return PokemonUtility.getAllMoves(moves).stream()
                .filter(move -> config.levelMove || moves.getLevelUpMoves().values().stream().noneMatch(list -> list.contains(move)))
                .filter(move -> config.eggMove || !moves.getEggMoves().contains(move))
                .filter(move -> config.tutorMove || !moves.getTutorMoves().contains(move))
                .filter(move -> config.tmMove || !moves.getTmMoves().contains(move))
                .filter(move -> config.legacyMove || !moves.getLegacyMoves().contains(move))
                .filter(move -> config.specialMove || !moves.getSpecialMoves().contains(move))
                .filter(move -> !config.hideAlreadyKnownMoves || !PokemonUtility.isLearnedMove(this.selectedPokemon, move))
                .collect(Collectors.toSet());
    }
}
