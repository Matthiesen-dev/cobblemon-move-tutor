package dev.matthiesen.common.cobblemon_move_tutor.util;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class PokemonUtility {
    public static Component[] loreBuilder(Pokemon pokemon) {
        String moveOne = !pokemon.getMoveSet().getMoves().isEmpty() ?
                Objects.requireNonNull(pokemon.getMoveSet().get(0)).getDisplayName().getString() : "Empty";
        String moveTwo = pokemon.getMoveSet().getMoves().size() >= 2 ?
                Objects.requireNonNull(pokemon.getMoveSet().get(1)).getDisplayName().getString() : "Empty";
        String moveThree = pokemon.getMoveSet().getMoves().size() >= 3 ?
                Objects.requireNonNull(pokemon.getMoveSet().get(2)).getDisplayName().getString() : "Empty";
        String moveFour = pokemon.getMoveSet().getMoves().size() >= 4 ?
                Objects.requireNonNull(pokemon.getMoveSet().get(3)).getDisplayName().getString() : "Empty";

        return new Component[]{
                Component.literal(pokemon.getCaughtBall().item().getDefaultInstance().getDisplayName().getString())
                        .setStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.DARK_GRAY)),
                Component.literal("Level: ").withStyle(ChatFormatting.AQUA)
                        .append(Component.literal(String.valueOf(pokemon.getLevel())).withStyle(ChatFormatting.WHITE)),
                Component.literal("Nickname: ").withStyle(ChatFormatting.DARK_GREEN)
                        .append(Component.literal(
                        pokemon.getNickname() != null ? pokemon.getNickname().getString() : "No nickname"
                ).withStyle(ChatFormatting.WHITE)),
                Component.literal("Moves: ").withStyle(ChatFormatting.DARK_GREEN),
                Component.literal(" ").append(Component.literal(moveOne).withStyle(ChatFormatting.WHITE)),
                Component.literal(" ").append(Component.literal(moveTwo).withStyle(ChatFormatting.WHITE)),
                Component.literal(" ").append(Component.literal(moveThree).withStyle(ChatFormatting.WHITE)),
                Component.literal(" ").append(Component.literal(moveFour).withStyle(ChatFormatting.WHITE))
        };
    }

    public static MutableComponent customNameBuilder(Pokemon pokemon) {
        return pokemon.getShiny() ?
                pokemon.getSpecies().getTranslatedName().copy().withStyle(ChatFormatting.GRAY)
                .append(Component.literal(" ★").withStyle(ChatFormatting.GOLD)) :
                pokemon.getSpecies().getTranslatedName().copy().withStyle(ChatFormatting.GRAY);
    }

    public static ItemStack pokemonToItem(Pokemon pokemon) {
        return new ItemBuilder(PokemonItem.from(pokemon, 1))
                .hideAdditional()
                .addLore(loreBuilder(pokemon))
                .setCustomName(customNameBuilder(pokemon))
                .build();
    }

    public static ItemStack getItemTM(MoveTemplate move) {
        var itemStack = CobblemonItems.NORMAL_GEM.getDefaultInstance();

        switch (move.getElementalType().getName()) {
            case "Bug" -> itemStack = CobblemonItems.BUG_GEM.getDefaultInstance();
            case "Dark" -> itemStack = CobblemonItems.DARK_GEM.getDefaultInstance();
            case "Dragon" -> itemStack = CobblemonItems.DRAGON_GEM.getDefaultInstance();
            case "Electric" -> itemStack = CobblemonItems.ELECTRIC_GEM.getDefaultInstance();
            case "Fairy" -> itemStack = CobblemonItems.FAIRY_GEM.getDefaultInstance();
            case "Ice" -> itemStack = CobblemonItems.ICE_GEM.getDefaultInstance();
            case "Ghost" -> itemStack = CobblemonItems.GHOST_GEM.getDefaultInstance();
            case "Flying" -> itemStack = CobblemonItems.FLYING_GEM.getDefaultInstance();
            case "Fighting" -> itemStack = CobblemonItems.FIGHTING_GEM.getDefaultInstance();
            case "Ground" -> itemStack = CobblemonItems.GROUND_GEM.getDefaultInstance();
            case "Normal" -> itemStack = CobblemonItems.NORMAL_GEM.getDefaultInstance();
            case "Psychic" -> itemStack = CobblemonItems.PSYCHIC_GEM.getDefaultInstance();
            case "Rock" -> itemStack = CobblemonItems.ROCK_GEM.getDefaultInstance();
            case "Fire" -> itemStack = CobblemonItems.FIRE_GEM.getDefaultInstance();
            case "Poison" -> itemStack = CobblemonItems.POISON_GEM.getDefaultInstance();
            case "Steel" -> itemStack = CobblemonItems.STEEL_GEM.getDefaultInstance();
            case "Grass" -> itemStack = CobblemonItems.GRASS_GEM.getDefaultInstance();
            case "Water" -> itemStack = CobblemonItems.WATER_GEM.getDefaultInstance();
        }

        return itemStack;
    }

    public static List<MoveTemplate> getAllMoves(Learnset moves) {
        List<MoveTemplate> allMoveList = new ArrayList<>();

        allMoveList.addAll(moves.getAllLegalMoves());
        allMoveList.addAll(moves.getLegacyMoves());
        allMoveList.addAll(moves.getSpecialMoves());

        return allMoveList;
    }

    public static boolean isLearnedMove(Pokemon pokemon, MoveTemplate move) {
        Set<MoveTemplate> moves = new HashSet<>(pokemon.getAllAccessibleMoves());

        for (Move learnedMove : pokemon.getMoveSet().getMoves()) {
            moves.add(learnedMove.getTemplate());
        }

        return moves.contains(move);
    }

    public static int getMovePrice(Pokemon pokemon, MoveTemplate move) {
        Learnset moves = pokemon.getForm().getMoves();

        if (moves.getLevelUpMoves().values().stream().anyMatch(list -> list.contains(move))) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.levelMovePrice;
        }

        if (moves.getTmMoves().contains(move)) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.tmMovePrice;
        }

        if (moves.getLegacyMoves().contains(move)) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.legacyMovePrice;
        }

        if (moves.getTutorMoves().contains(move)) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.tutorMovePrice;
        }

        if (moves.getSpecialMoves().contains(move)) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.specialMovePrice;
        }

        if (moves.getEggMoves().contains(move)) {
            return CobblemonMoveTutorCommon.getCommonConfig().currencyConfig.eggMovePrice;
        }

        return 0;
    }

    public static String formatStatMove(double stat) {
        if (stat <= 0) {
            return "-";
        }

        return String.valueOf(stat);
    }

    private static String getCategoryIcon(MoveTemplate Move) {
        return switch (Move.getDamageCategory().getName()) {
            case "physical" -> " ⚔";
            case "special" -> " ⚡";
            default -> " 🧪";
        };
    }

    public static Component[] getMoveLore(Pokemon pokemon, MoveTemplate move) {
        List<Component> lore = new ArrayList<>();

        var type = move.getElementalType().getDisplayName().copy()
                .withColor(move.getElementalType().getHue());
        var typeLine = Component.translatable("cobblemon_move_tutor.gui.lore.type", type).withStyle(ChatFormatting.GRAY)
                .append(getCategoryIcon(move));

        lore.add(typeLine);
        lore.add(Component.translatable("cobblemon_move_tutor.gui.lore.power", PokemonUtility.formatStatMove(move.getPower())).withStyle(ChatFormatting.GRAY));
        lore.add(Component.translatable("cobblemon_move_tutor.gui.lore.accuracy", PokemonUtility.formatStatMove(move.getAccuracy())).withStyle(ChatFormatting.GRAY));
        lore.add(Component.translatable("cobblemon_move_tutor.gui.lore.pp", String.valueOf(move.getPp()), String.valueOf(move.getMaxPp())).withStyle(ChatFormatting.GRAY));

        var price = PokemonUtility.getMovePrice(pokemon, move);

        if (price > 0) {
            var serverConfig = CobblemonMoveTutorCommon.getCommonConfig();
            var currencyProvider = CobblemonMoveTutorCommon.currencyProviderRegistry.get(serverConfig.currencyConfig.currencyType);

            if (currencyProvider == null) {
                lore.add(Component.translatable("cobblemon_move_tutor.msg.invalidCurrency", serverConfig.currencyConfig.currencyType).withStyle(ChatFormatting.RED));
            } else {
                lore.add(currencyProvider.lore(price).copy().withStyle(ChatFormatting.YELLOW));
            }
        } else {
            lore.add(Component.translatable("cobblemon_move_tutor.gui.lore.free").withStyle(ChatFormatting.GREEN));
        }

        return lore.toArray(new Component[0]);
    }

    public static Set<MoveTemplate> getFilteredMoves(Pokemon pokemon, CommonConfig.TutorConfig config) {
        Learnset moves = pokemon.getForm().getMoves();
        return PokemonUtility.getAllMoves(moves).stream()
                .filter(move -> config.levelMove || moves.getLevelUpMoves().values().stream().noneMatch(list -> list.contains(move)))
                .filter(move -> config.eggMove || !moves.getEggMoves().contains(move))
                .filter(move -> config.tutorMove || !moves.getTutorMoves().contains(move))
                .filter(move -> config.tmMove || !moves.getTmMoves().contains(move))
                .filter(move -> config.legacyMove || !moves.getLegacyMoves().contains(move))
                .filter(move -> config.specialMove || !moves.getSpecialMoves().contains(move))
                .filter(move -> !config.hideAlreadyKnownMoves || !PokemonUtility.isLearnedMove(pokemon, move))
                .collect(Collectors.toSet());
    }

    public static Button getEmptyPokemonButton() {
        return GooeyButton.builder()
                .display(
                        new ItemBuilder(
                                ItemDecoder.stringToItem(
                                        CobblemonMoveTutorCommon.getGuiConfig().emptyPokemonId,
                                        CobblemonItems.POKE_BALL
                                ))
                                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.emptySlot").withStyle(ChatFormatting.GRAY))
                                .hideAdditional()
                                .build()
                )
                .onClick(action ->
                        new SoundsPlayer(CobblemonSounds.POKE_BALL_HIT).play(action.getPlayer()))
                .build();
    }

    public static ItemStack getMoveItem(MoveTemplate move, Pokemon selectedPokemon) {
        return new ItemBuilder(PokemonUtility.getItemTM(move))
                .hideAdditional()
                .setCustomName(move.getDisplayName())
                .addLore(PokemonUtility.getMoveLore(selectedPokemon, move))
                .build();
    }

    public static Button getDetailsButton(MoveTemplate move, Pokemon selectedPokemon) {
        return GooeyButton.builder().display(getMoveItem(move, selectedPokemon)).build();
    }
}
