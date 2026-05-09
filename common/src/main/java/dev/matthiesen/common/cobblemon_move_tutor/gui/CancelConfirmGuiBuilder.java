package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.interfaces.IGui;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemDecoder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public record CancelConfirmGuiBuilder(
        ServerPlayer player,
        Component title,
        Consumer<ButtonAction> yesButtonAction,
        Consumer<ButtonAction> noButtonAction,
        Button optionalDetails
) implements IGui {

    public CancelConfirmGuiBuilder(
            ServerPlayer player,
            Component title,
            Runnable yesButtonAction,
            Runnable noButtonAction,
            Button optionalDetails
    ) {
        this(
                player,
                title,
                (yesButtonAction != null) ? (action) -> yesButtonAction.run() : null,
                (noButtonAction != null) ? (action) -> noButtonAction.run() : null,
                optionalDetails
        );
    }

    public Component getTitle() {
        return title;
    }

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

    public static ItemStack getNoItem() {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.getGuiConfig().cancelItemId))
                .hideAdditional()
                .setCustomName(
                        Component.translatable("cobblemon_move_tutor.gui.button.cancel")
                                .withStyle(ChatFormatting.RED)
                )
                .build();
    }

    public static ItemStack getYesItem() {
        return new ItemBuilder(ItemDecoder.stringToItem(CobblemonMoveTutorCommon.getGuiConfig().confirmItemId))
                .hideAdditional()
                .setCustomName(
                        Component.translatable("cobblemon_move_tutor.gui.button.confirm")
                                .withStyle(ChatFormatting.GREEN)
                )
                .build();
    }

    public Page getPage() {
        Button cancelButton = GooeyButton.builder()
                .display(getNoItem())
                .onClick(noButtonAction)
                .build();

        Button confirmButton = GooeyButton.builder()
                .display(getYesItem())
                .onClick(yesButtonAction)
                .build();

        ChestTemplate.Builder builder = ChestTemplate.builder(3)
                .row(0, getFrame())
                .row(1, getFrame())
                .set(1, 3, cancelButton)
                .set(1, 5, confirmButton)
                .row(2, getFrame());

        if (optionalDetails != null) {
            builder = builder.set(1, 4, optionalDetails);
        }

        ChestTemplate template = builder.build();
        GooeyPage page = GooeyPage.builder().template(template).build();
        page.setTitle(getTitle());
        return page;
    }

    @Override
    public void open() {
        UIManager.openUIForcefully(player, getPage());
    }

    @Override
    public void close() {
        UIManager.closeUI(player);
    }

    @Override
    public void sendPlayerMessage(Component message) {
        player.sendSystemMessage(message);
    }
}