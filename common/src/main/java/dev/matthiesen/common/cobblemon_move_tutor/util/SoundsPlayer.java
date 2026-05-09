package dev.matthiesen.common.cobblemon_move_tutor.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundsPlayer {
    private final SoundEvent soundEvent;
    private final SoundSource soundSource = SoundSource.MASTER;
    private final Float volume = 1.0F;
    private final Float pitch = 1.0F;

    public SoundsPlayer(SoundEvent sound) {
        this.soundEvent = sound;
    }

    public void play(ServerPlayer player) {
        player.server.executeIfPossible(
                () -> player.playNotifySound(this.soundEvent, this.soundSource, this.volume, this.pitch)
        );
    }
}
