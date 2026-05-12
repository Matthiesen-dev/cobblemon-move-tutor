package dev.matthiesen.common.cobblemon_move_tutor.ui.buttons;

import net.minecraft.world.Container;

public class NoHighlightButton extends Button {
    public NoHighlightButton(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean isHighlightable() { return false; }
}
