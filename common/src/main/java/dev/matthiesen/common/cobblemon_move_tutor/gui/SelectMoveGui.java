package dev.matthiesen.common.cobblemon_move_tutor.gui;

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
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class SelectMoveGui extends Abstract9x3Gui {
    public ServerPlayer player;
    public Pokemon selectedPokemon;
    public List<MoveTemplate> moves;
    public String type;

    public SelectMoveGui(ServerPlayer player, Pokemon selectedPokemon, String type) {
        this.player = player;
        this.selectedPokemon = selectedPokemon;
        this.type = type;
        this.moves = PokemonUtility.getFilteredMoves(selectedPokemon, getTutorConfig()).stream().toList();
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();

        for (MoveTemplate move : moves) {
            buttons.add(
                    GooeyButton.builder()
                            .display(PokemonUtility.getMoveItem(move, selectedPokemon))
                            .onClick(action -> {
                                ServerPlayer sender = action.getPlayer();
                                new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sender);
                                new ConfirmationGui(sender, "Confirm Move Teaching",
                                        () -> MoveManager.learnMove(sender, selectedPokemon, move, this::open),
                                        this::open, PokemonUtility.getDetailsButton(move, selectedPokemon)).open();
                            })
                            .build()
            );
        }
        return buttons;
    }

    @Override
    public String getTitle() {
        return "Select a Move";
    }

    private CommonConfig.TutorConfig getTutorConfig() {
        if (type == null || type.isEmpty() || !type.equals("admin")) {
            return CobblemonMoveTutorCommon.getCommonConfig().villageTutorConfig;
        }
        return CobblemonMoveTutorCommon.getCommonConfig().adminTutorConfig;
    }

    @Override
    public Page getPage() {
        List<Button> buttons = getButtons();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(getNavItem("cobblemon_move_tutor.gui.button.previousPage"))
                .linkType(LinkType.Previous)
                .onClick((action) -> new SoundsPlayer(CobblemonSounds.PC_CLICK).play(action.getPlayer()))
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(getNavItem("cobblemon_move_tutor.gui.button.nextPage"))
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

    public ItemStack getPageItem(int currentPage, int pageLength) {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.getGuiConfig().currentPageItemId, Items.PAPER))
                .setCustomName(
                        Component.translatable(
                                "cobblemon_move_tutor.gui.button.pageIndicator",
                                String.valueOf(currentPage),
                                String.valueOf(pageLength)
                        ).withStyle(ChatFormatting.GOLD)
                )
                .build();
    }

    public ItemStack getNavItem(String label) {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.getGuiConfig().navigationItemId, Items.ARROW))
                .hideAdditional()
                .setCustomName(Component.translatable(label).withStyle(ChatFormatting.AQUA))
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
