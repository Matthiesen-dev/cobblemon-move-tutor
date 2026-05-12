package dev.matthiesen.common.cobblemon_move_tutor.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundsPlayer {
    private final SoundEvent soundEvent;
    private final SoundSource soundSource = SoundSource.MASTER;
    private Float volume = 1.0F;
    private Float pitch = 1.0F;

    public SoundsPlayer(SoundEvent sound) {
        this.soundEvent = sound;
    }

    @SuppressWarnings("unused")
    public SoundsPlayer setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    @SuppressWarnings("unused")
    public SoundsPlayer setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public void play(ServerPlayer player) {
        player.server.executeIfPossible(() ->
                player.playNotifySound(this.soundEvent, this.soundSource, this.volume, this.pitch));
    }
}
