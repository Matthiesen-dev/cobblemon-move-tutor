package dev.matthiesen.cobblemon_move_tutor.common.ui.client;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.ui.SelectMoveMenu;
import dev.matthiesen.matthiesen_core.common.utility.ui.screen.AbstractSimpleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class SelectMoveScreen extends AbstractSimpleScreen<SelectMoveMenu> {
    private static final int BG_WIDTH = 176;
    private static final int BG_HEIGHT = 138;
    private static final ResourceLocation BACKGROUND =
            CobblemonMoveTutor.modResource("textures/gui/select_move.png");

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

