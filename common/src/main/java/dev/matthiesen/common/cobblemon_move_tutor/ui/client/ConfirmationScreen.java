package dev.matthiesen.common.cobblemon_move_tutor.ui.client;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ConfirmationScreen extends AbstractScreen<ConfirmationMenu> {
    private static final int BG_WIDTH = 176;
    private static final int BG_HEIGHT = 82;
    private static final ResourceLocation BACKGROUND =
            Constants.modResource("textures/gui/confirmation.png");

    @Override
    protected int getBgWidth() {
        return BG_WIDTH;
    }

    @Override
    protected int getBgHeight() {
        return BG_HEIGHT;
    }

    @Override
    protected ResourceLocation getBackgroundTexture() {
        return BACKGROUND;
    }

    public ConfirmationScreen(ConfirmationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
