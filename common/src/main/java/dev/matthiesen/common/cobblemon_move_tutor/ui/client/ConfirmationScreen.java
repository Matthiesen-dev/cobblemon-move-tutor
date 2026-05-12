package dev.matthiesen.common.cobblemon_move_tutor.ui.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ConfirmationScreen extends AbstractContainerScreen<ConfirmationMenu> {

    private static final ResourceLocation BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/confirmation.png");

    /** The actual pixel size of confirmation.png */
    private static final int BG_WIDTH  = 176;
    private static final int BG_HEIGHT = 82;

    public ConfirmationScreen(ConfirmationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        // Tell AbstractContainerScreen our actual background size so slot positions
        // and mouse hit-testing work correctly.
        this.imageWidth  = BG_WIDTH;
        this.imageHeight = BG_HEIGHT;
    }

    // ── Rendering ────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Draw dim background overlay behind the window
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = leftPos;
        int y = topPos;

        // blit(texture, screenX, screenY, srcU, srcV, width, height, textureWidth, textureHeight)
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);
    }

    /**
     * Suppress the default container-title and player-inventory labels that
     * AbstractContainerScreen normally draws over the background.
     */
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Intentionally empty – we have no text labels to draw.
    }
}
