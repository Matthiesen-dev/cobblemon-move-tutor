package dev.matthiesen.common.cobblemon_move_tutor.ui;

import com.cobblemon.mod.common.CobblemonSounds;
import dev.architectury.registry.menu.MenuRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ModelData;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfirmationMenu extends AbstractContainerMenu {

    // Slot indices
    public static final int CANCEL_SLOT  = 0;
    public static final int DETAILS_SLOT = 1;
    public static final int CONFIRM_SLOT = 2;
    public static final int TITLE_SLOT   = 3;

    // Slot positions inside the 176×82 background image
    private static final int SLOT_Y       = 36;
    private static final int CANCEL_X     = 62;
    private static final int DETAILS_X    = 80;
    private static final int CONFIRM_X    = 98;
    // Title sits in row 0, col 4 of the old 9×3 chest grid → x = 8 + 4*18 = 80, y = 18
    private static final int TITLE_X      = 80;
    private static final int TITLE_Y      = 18;

    private final SimpleContainer container;

    // Server-side only – null on the client
    @Nullable private Runnable confirmCallback;
    @Nullable private Runnable cancelCallback;
    private boolean resolved = false;

    // ── Server-side constructor (called via openFor) ─────────────────────────
    public ConfirmationMenu(int containerID, Inventory inventory,
                            ItemStack detailsItem,
                            Runnable confirmCallback,
                            Runnable cancelCallback) {
        super(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), containerID);
        this.confirmCallback = confirmCallback;
        this.cancelCallback  = cancelCallback;
        this.container = new SimpleContainer(4);
        this.container.setItem(CANCEL_SLOT,  buildCancelItem());
        this.container.setItem(DETAILS_SLOT, detailsItem.copy());
        this.container.setItem(CONFIRM_SLOT, buildConfirmItem());
        this.container.setItem(TITLE_SLOT,   buildTitleItem());
        addDisplaySlots();
    }

    // ── Client-side factory constructor (bound to the MenuType) ──────────────
    public ConfirmationMenu(int containerID, Inventory inventory) {
        super(MenuTypesRegistry.CONFIRMATION_SCREEN.get(), containerID);
        this.container = new SimpleContainer(4);
        addDisplaySlots();
    }

    // ── Slot registration ────────────────────────────────────────────────────
    private void addDisplaySlots() {
        // Display-only: players cannot pick up or place items
        addSlot(new Slot(container, CANCEL_SLOT,  CANCEL_X,  SLOT_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
        });
        addSlot(new Slot(container, DETAILS_SLOT, DETAILS_X, SLOT_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
        });
        addSlot(new Slot(container, CONFIRM_SLOT, CONFIRM_X, SLOT_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
        });
        // Title slot – mirrors getTitleButton() at old chest slot (row 0, col 4)
        addSlot(new Slot(container, TITLE_SLOT, TITLE_X, TITLE_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
            @Override public boolean isHighlightable() { return false; }
        });
    }

    // ── Slot click → callback ────────────────────────────────────────────────
    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;
        if (slotId == CANCEL_SLOT) {
            // Mark resolved BEFORE closeContainer so that removed() is a no-op,
            // then close, then fire the callback – this way the callback can open
            // a new screen without immediately having it closed again.
            resolved = true;
            new SoundsPlayer(CobblemonSounds.POKE_BALL_HIT).play(sp);
            sp.closeContainer();
            if (cancelCallback != null) cancelCallback.run();
        } else if (slotId == CONFIRM_SLOT) {
            resolved = true;
            new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sp);
            sp.closeContainer();
            if (confirmCallback != null) confirmCallback.run();
        }
        // Details slot: do nothing – item movement blocked by Slot overrides.
    }

    // ESC / inventory close without clicking → treat as cancel
    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!resolved) {
            resolved = true;
            if (cancelCallback != null) cancelCallback.run();
        }
    }

    // ── Static factory to open the screen from server code ───────────────────
    public static void openFor(ServerPlayer player,
                               ItemStack detailsItem,
                               Runnable confirmCallback,
                               Runnable cancelCallback) {
        MenuRegistry.openMenu(player, new SimpleMenuProvider(
                (id, inv, p) -> new ConfirmationMenu(id, inv, detailsItem, confirmCallback, cancelCallback),
                Component.empty()
        ));
    }

    // ── Item builders ─────────────────────────────────────────────────────────
    private static ItemStack buildCancelItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_BUTTON.GUI_CANCEL)
                .hideAdditional()
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.cancel")
                        .withStyle(ChatFormatting.RED))
                .build();
    }

    private static ItemStack buildConfirmItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_BUTTON.GUI_CONFIRM)
                .hideAdditional()
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.confirm")
                        .withStyle(ChatFormatting.GREEN))
                .build();
    }

    /** Replicates the old {@code getTitleButton()} – invisible item that drives the CONFIRM_TEACH overlay. */
    private static ItemStack buildTitleItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_TEXT.CONFIRM_TEACH)
                .hideAdditional()
                .setCustomName(Component.literal(" "))
                .build();
    }

    // ── Vanilla overrides ─────────────────────────────────────────────────────
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
