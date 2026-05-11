package dev.matthiesen.common.cobblemon_move_tutor.gui;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.CustomModels;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class ConfirmationGui extends Abstract9x3Gui {
    private final ServerPlayer player;
    private final String title;
    private final Button optionalDetails;
    private final Button cancelButton;
    private final Button confirmButton;

    public ConfirmationGui(
            ServerPlayer player,
            String title,
            Runnable yesButtonAction,
            Runnable noButtonAction,
            Button optionalDetails
    ) {
        this.player = player;
        this.title = title;
        this.optionalDetails = optionalDetails;
        this.cancelButton = GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                                .setModelData(CustomModels.GUI_BUTTON.GUI_CANCEL)
                                .hideAdditional()
                                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.cancel")
                                        .withStyle(ChatFormatting.RED))
                                .build()
                )
                .onClick(noButtonAction)
                .build();
        this.confirmButton = GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                                .hideAdditional()
                                .setModelData(CustomModels.GUI_BUTTON.GUI_CONFIRM)
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
    public String getTitle() {
        return title;
    }

    @Override
    public List<Button> getButtons() {
        return List.of(cancelButton, optionalDetails, confirmButton);
    }

    public Button getTitleButton() {
        return GooeyButton.builder()
                .display(
                        new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                                .setModelData(CustomModels.GUI_TEXT.CONFIRM_TEACH)
                                .hideAdditional()
                                .setCustomName(Component.literal(" "))
                                .build()
                )
                .build();
    }

    @Override
    public ChestTemplate getTemplate() {
        return ChestTemplate.builder(3)
                .set(0, 4, getTitleButton())
                .set(1, 3, getPlaceholder())
                .set(1, 4, getPlaceholder())
                .set(1, 5, getPlaceholder())
                .build();
    }
}
