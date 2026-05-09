package dev.matthiesen.common.cobblemon_move_tutor.interfaces;

import net.minecraft.network.chat.Component;

public interface IGui {
    void open();
    void close();
    void sendPlayerMessage(Component message);
}
