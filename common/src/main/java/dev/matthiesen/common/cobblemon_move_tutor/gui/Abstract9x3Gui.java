package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.IGui;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemDecoder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public abstract class Abstract9x3Gui implements IGui {
    public abstract ServerPlayer getPlayer();
    public abstract List<Button> getButtons();
    public abstract Component getTitle();

    public static String getFrameIdentifier() {
        return CobblemonMoveTutorCommon.getGuiConfig().frameItemId;
    }

    public static Button getFrame() {
        Item item = ItemDecoder.stringToItem(getFrameIdentifier());
        Item itemToDisplay;

        if (item != Items.AIR) {
            itemToDisplay = item;
        } else {
            itemToDisplay = Items.GRAY_STAINED_GLASS_PANE;
        }

        return GooeyButton.builder()
                .display(
                        new ItemBuilder(itemToDisplay)
                                .hideAdditional()
                                .setCustomName(Component.literal(" "))
                                .build()
                )
                .build();
    }

    public static PlaceholderButton getPlaceholder() {
        return new PlaceholderButton();
    }

    /**
     * Can be overridden to allow for a different center button, but by default it will just be the same as the frame
     */
    public static Button getCenterButton() {
        return getFrame();
    }

    public Page getPage() {
        List<Button> buttons = getButtons();

        ChestTemplate template = ChestTemplate.builder(3)
                .row(0, getFrame())
                .row(1, getFrame())
                .set(1, 1, getPlaceholder())
                .set(1, 2, getPlaceholder())
                .set(1, 3, getPlaceholder())
                .set(1, 4, getCenterButton())
                .set(1, 5, getPlaceholder())
                .set(1, 6, getPlaceholder())
                .set(1, 7, getPlaceholder())
                .row(2, getFrame())
                .build();

        GooeyPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
        page.setTitle(getTitle());
        return page;
    }

    @Override
    public void open() {
        UIManager.openUIForcefully(getPlayer(), getPage());
    }

    @Override
    public void close() {
        UIManager.closeUI(getPlayer());
    }

    @Override
    public void sendPlayerMessage(Component message) {
        getPlayer().sendSystemMessage(message);
    }
}
