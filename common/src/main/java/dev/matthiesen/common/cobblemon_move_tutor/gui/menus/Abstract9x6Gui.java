package dev.matthiesen.common.cobblemon_move_tutor.gui.menus;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.CobblemonSounds;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemDecoder;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class Abstract9x6Gui extends Abstract9x3Gui {
    @Override
    public Page getPage() {
        List<Button> buttons = getButtons();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(getNavItem("Previous"))
                .linkType(LinkType.Previous)
                .onClick((action) -> new SoundsPlayer(CobblemonSounds.PC_CLICK).play(action.getPlayer()))
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(getNavItem("Next"))
                .linkType(LinkType.Next)
                .onClick((action) -> new SoundsPlayer(CobblemonSounds.PC_CLICK).play(action.getPlayer()))
                .build();

        ChestTemplate template = ChestTemplate.builder(6)
                .rectangle(0, 0, 5, 9, getPlaceholder())
                .set(53, next)
                .set(47, getCenterButton())
                .set(49, getInfoButton(1, 1))
                .set(45, previous)
                .fill(getFrame())
                .build();

        LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
        setPageTitleRecursive(page);
        return page;
    }

    public static ItemStack getPageItem(int currentPage, int pageLength) {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.guiConfig.currentPageItemId))
                .setCustomName(
                        Component.literal("Page " + currentPage + "/" + pageLength).withStyle(style -> style.withColor(ChatFormatting.GOLD))
                )
                .build();
    }

    public static ItemStack getNavItem(String label) {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.guiConfig.navigationItemId))
                .hideAdditional()
                .setCustomName(
                        Component.literal(label)
                                .withStyle(
                                        style -> style.withColor(ChatFormatting.AQUA)
                                )
                )
                .build();
    }

    public Button getInfoButton(int currentPage, int pageLength) {
        return GooeyButton.builder()
                .display(getPageItem(currentPage, pageLength))
                .build();
    }

    public TemplateSlotDelegate getInfoButtonTemplate(int currentPage, int pageLength) {
        Button infoButton = getInfoButton(pageLength, currentPage);
        return new TemplateSlotDelegate(infoButton, 49);
    }

    public void setPageTitleInternal(LinkedPage page, int pageLength) {
        int currentPage = page.getCurrentPage();
        page.setTitle(getTitle());
        page.getTemplate().setSlot(49, getInfoButtonTemplate(pageLength, currentPage));
    }

    public void setPageTitleRecursive(LinkedPage page) {
        int pageLength = page.getTotalPages();
        setPageTitleInternal(page, pageLength);
        LinkedPage next = page.getNext();
        if (next != null) {
            setPageTitleInternal(next, pageLength);
            setPageTitleRecursive(next);
        }
    }
}
