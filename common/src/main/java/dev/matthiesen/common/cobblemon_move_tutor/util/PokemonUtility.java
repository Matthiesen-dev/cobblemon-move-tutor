package dev.matthiesen.common.cobblemon_move_tutor.util;

import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

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
}
