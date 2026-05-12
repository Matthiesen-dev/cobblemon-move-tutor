package dev.matthiesen.common.cobblemon_move_tutor.ui;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.architectury.registry.menu.MenuRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.*;
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

import java.util.ArrayList;
import java.util.List;

public class SelectMoveMenu extends AbstractContainerMenu {

    // ── Pagination constant ───────────────────────────────────────────────────
    /** Move slots per page: rows 1-4 × cols 1-7 in the old 6-row chest grid. */
    public static final int PAGE_SIZE = 28;

    // ── Slot indices ─────────────────────────────────────────────────────────
    public static final int TITLE_SLOT      = 0;
    public static final int FIRST_MOVE_SLOT = 1;   // 1 … 28
    public static final int PREV_SLOT       = 29;
    public static final int PAGE_SLOT       = 30;
    public static final int NEXT_SLOT       = 31;

    // ── Pixel positions within the 176×138 background image ──────────────────
    // Standard chest offsets: slot (row, col) → x = 8 + col*18, y = 18 + row*18
    private static final int TITLE_X = 80, TITLE_Y = 18;    // row 0, col 4
    private static final int NAV_Y   = 108;                  // row 5
    private static final int PREV_X  = 8;                    //        col 0
    private static final int PAGE_X  = 80;                   //        col 4
    private static final int NEXT_X  = 152;                  //        col 8

    private final SimpleContainer container;

    // Server-side only
    @Nullable private List<MoveTemplate> allMoves;
    @Nullable private Pokemon selectedPokemon;
    @Nullable private String type;
    private int currentPage = 0;

    // ── Server-side constructor ──────────────────────────────────────────────
    public SelectMoveMenu(int containerID, Inventory inventory,
                          ServerPlayer player, Pokemon pokemon, String type) {
        super(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), containerID);
        this.selectedPokemon = pokemon;
        this.type            = type;
        this.allMoves        = new ArrayList<>(PokemonUtility.getFilteredMoves(pokemon, getTutorConfig()));
        this.container       = new SimpleContainer(32);
        addDisplaySlots();
        populatePage(0);
    }

    // ── Client-side factory constructor ─────────────────────────────────────
    public SelectMoveMenu(int containerID, Inventory inventory) {
        super(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), containerID);
        this.container = new SimpleContainer(32);
        addDisplaySlots();
    }

    // ── Slot registration ────────────────────────────────────────────────────
    private void addDisplaySlots() {
        // Title slot
        addSlot(new Slot(container, TITLE_SLOT, TITLE_X, TITLE_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
            @Override public boolean isHighlightable() { return false; }
        });

        // Move slots: rows 1-4, cols 1-7  →  28 display slots
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 7; col++) {
                int si = (row - 1) * 7 + (col - 1) + FIRST_MOVE_SLOT;
                int x  = 8 + col * 18;
                int y  = 18 + row * 18;
                addSlot(new Slot(container, si, x, y) {
                    @Override public boolean mayPickup(Player p) { return false; }
                    @Override public boolean mayPlace(ItemStack s) { return false; }
                });
            }
        }

        // Navigation slots (row 5)
        addSlot(new Slot(container, PREV_SLOT, PREV_X, NAV_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
        });
        addSlot(new Slot(container, PAGE_SLOT, PAGE_X, NAV_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
            @Override public boolean isHighlightable() { return false; }
        });
        addSlot(new Slot(container, NEXT_SLOT, NEXT_X, NAV_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
        });
    }

    // ── Pagination ───────────────────────────────────────────────────────────
    private void populatePage(int page) {
        this.currentPage = page;
        int totalPages   = getTotalPages();

        // Clear all move slots
        for (int i = 0; i < PAGE_SIZE; i++) {
            container.setItem(FIRST_MOVE_SLOT + i, ItemStack.EMPTY);
        }
        // Fill current page
        int start = page * PAGE_SIZE;
        for (int i = 0; i < PAGE_SIZE; i++) {
            int moveIdx = start + i;
            if (allMoves != null && moveIdx < allMoves.size()) {
                container.setItem(FIRST_MOVE_SLOT + i,
                        PokemonUtility.getMoveItem(allMoves.get(moveIdx), selectedPokemon));
            }
        }
        // Update nav + title items
        container.setItem(TITLE_SLOT, buildTitleItem());
        container.setItem(PREV_SLOT,  buildPrevItem());
        container.setItem(PAGE_SLOT,  buildPageItem(page + 1, totalPages));
        container.setItem(NEXT_SLOT,  buildNextItem());
    }

    private int getTotalPages() {
        if (allMoves != null) {
            return Math.max(1, (allMoves.size() + PAGE_SIZE - 1) / PAGE_SIZE);
        }
        return 1;
    }

    // ── Slot click ───────────────────────────────────────────────────────────
    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (slotId == PREV_SLOT) {
            if (currentPage > 0) {
                new SoundsPlayer(CobblemonSounds.PC_CLICK).play(sp);
                populatePage(currentPage - 1);
                broadcastChanges();
            }
        } else if (slotId == NEXT_SLOT) {
            if (currentPage < getTotalPages() - 1) {
                new SoundsPlayer(CobblemonSounds.PC_CLICK).play(sp);
                populatePage(currentPage + 1);
                broadcastChanges();
            }
        } else if (slotId >= FIRST_MOVE_SLOT && slotId < FIRST_MOVE_SLOT + PAGE_SIZE) {
            int moveIdx = currentPage * PAGE_SIZE + (slotId - FIRST_MOVE_SLOT);
            if (allMoves != null && moveIdx < allMoves.size()) {
                MoveTemplate move = allMoves.get(moveIdx);
                new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sp);
                ItemStack moveItem = PokemonUtility.getMoveItem(move, selectedPokemon);
                ConfirmationMenu.openFor(sp, moveItem,
                        () -> MoveManager.learnMove(sp, selectedPokemon, move,
                                () -> SelectMoveMenu.openFor(sp, selectedPokemon, type)),
                        () -> SelectMoveMenu.openFor(sp, selectedPokemon, type)
                );
            }
        }
    }

    public static void openFor(ServerPlayer player, Pokemon pokemon, String type) {
        MenuRegistry.openMenu(player, new SimpleMenuProvider(
                (id, inv, p) -> new SelectMoveMenu(id, inv, player, pokemon, type),
                Component.empty()
        ));
    }

    private CommonConfig.TutorConfig getTutorConfig() {
        if (type == null || type.isEmpty() || !type.equals("admin")) {
            return CobblemonMoveTutorCommon.getCommonConfig().villageTutorConfig;
        }
        return CobblemonMoveTutorCommon.getCommonConfig().adminTutorConfig;
    }

    private static ItemStack buildTitleItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_TEXT.SELECT_MOVE)
                .hideAdditional()
                .setCustomName(Component.literal(" "))
                .build();
    }

    private static ItemStack buildPrevItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_BUTTON.GUI_PREVIOUS)
                .hideAdditional()
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.previousPage")
                        .withStyle(ChatFormatting.AQUA))
                .build();
    }

    private static ItemStack buildNextItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_BUTTON.GUI_NEXT)
                .hideAdditional()
                .setCustomName(Component.translatable("cobblemon_move_tutor.gui.button.nextPage")
                        .withStyle(ChatFormatting.AQUA))
                .build();
    }

    private static ItemStack buildPageItem(int current, int total) {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_BUTTON.GUI_PAGE)
                .hideAdditional()
                .setCustomName(Component.translatable(
                        "cobblemon_move_tutor.gui.button.pageIndicator",
                        String.valueOf(current),
                        String.valueOf(total)
                ).withStyle(ChatFormatting.GOLD))
                .build();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}





