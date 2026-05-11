package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.IGui;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public abstract class Abstract9x3Gui implements IGui {
    public abstract ServerPlayer getPlayer();
    public abstract List<Button> getButtons();
    public abstract String getTitle();

    public PlaceholderButton getPlaceholder() {
        return new PlaceholderButton();
    }

    public ChestTemplate getTemplate() {
        return ChestTemplate.builder(3)
                .set(1, 1, getPlaceholder())
                .set(1, 2, getPlaceholder())
                .set(1, 3, getPlaceholder())
                .set(1, 5, getPlaceholder())
                .set(1, 6, getPlaceholder())
                .set(1, 7, getPlaceholder())
                .build();
    }

    public Page getPage() {
        List<Button> buttons = getButtons();
        ChestTemplate template = getTemplate();
        GooeyPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
        page.setTitle(Component.literal(getTitle()).withStyle(ChatFormatting.WHITE));
        return page;
    }

    @Override
    public void open() {
        UIManager.openUIForcefully(getPlayer(), getPage());
    }
}
