package dev.matthiesen.common.cobblemon_move_tutor.ui.buttons;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class Button extends Slot {
    public Button(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPickup(Player p) { return false; }

    @Override
    public boolean mayPlace(ItemStack s) { return false; }
}
