package dev.matthiesen.common.cobblemon_move_tutor.interfaces;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface ICurrencyProvider {
    Component lore(int price);
    boolean buy(ServerPlayer player, Pokemon pokemon, MoveTemplate move, int price);
    void successfulBuyMessage(ServerPlayer player, Pokemon pokemon, MoveTemplate move, int price);
}