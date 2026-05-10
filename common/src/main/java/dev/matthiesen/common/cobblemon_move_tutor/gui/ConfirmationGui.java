package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemDecoder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;

import java.util.List;

public class ConfirmationGui extends Abstract9x3Gui {
    private final ServerPlayer player;
    private final Component title;
    private final Button optionalDetails;
    private final Button cancelButton;
    private final Button confirmButton;

    public ConfirmationGui(
            ServerPlayer player,
            Component title,
            Runnable yesButtonAction,
            Runnable noButtonAction,
            Button optionalDetails
    ) {
        this.player = player;
        this.title = title;
        this.optionalDetails = optionalDetails;
        this.cancelButton = GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemDecoder.stringToItem(
                                CobblemonMoveTutorCommon.getGuiConfig().cancelItemId,
                                Items.RED_STAINED_GLASS_PANE))
                                .hideAdditional()
                                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.cancel")
                                        .withStyle(ChatFormatting.RED))
                                .build()
                )
                .onClick(noButtonAction)
                .build();
        this.confirmButton = GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemDecoder.stringToItem(
                                CobblemonMoveTutorCommon.getGuiConfig().confirmItemId,
                                Items.GREEN_STAINED_GLASS_PANE))
                                .hideAdditional()
                                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.confirm")
                                        .withStyle(ChatFormatting.GREEN))
                                .build())
                .onClick(yesButtonAction)
                .build();
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public List<Button> getButtons() {
        return List.of();
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public ChestTemplate getTemplate() {
        return ChestTemplate.builder(3)
                .row(0, getFrame())
                .row(1, getFrame())
                .row(2, getFrame())
                .set(1, 3, cancelButton)
                .set(1, 4, optionalDetails)
                .set(1, 5, confirmButton).build();
    }
}
