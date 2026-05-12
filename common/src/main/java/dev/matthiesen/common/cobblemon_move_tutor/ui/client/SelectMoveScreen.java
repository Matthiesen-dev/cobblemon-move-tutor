package dev.matthiesen.common.cobblemon_move_tutor.ui.client;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SelectMoveScreen extends AbstractScreen<SelectMoveMenu> {
    private static final int BG_WIDTH  = 176;
    private static final int BG_HEIGHT = 138;
    private static final ResourceLocation BACKGROUND =
            Constants.modResource("textures/gui/select_move.png");

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

    public SelectMoveScreen(SelectMoveMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}

