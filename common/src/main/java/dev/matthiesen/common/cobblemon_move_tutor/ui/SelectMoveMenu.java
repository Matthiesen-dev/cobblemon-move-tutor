package dev.matthiesen.common.cobblemon_move_tutor.ui;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.config.CommonConfig;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.ui.buttons.Button;
import dev.matthiesen.common.cobblemon_move_tutor.ui.buttons.NoHighlightButton;
import dev.matthiesen.common.cobblemon_move_tutor.ui.buttons.StaticButtons;
import dev.matthiesen.common.cobblemon_move_tutor.util.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SelectMoveMenu extends AbstractMenu {

    public static final int PAGE_SIZE = 28;

    public static final int TITLE_SLOT = 0;
    public static final int FIRST_MOVE_SLOT = 1;   // 1 … 28
    public static final int PREV_SLOT = 29;
    public static final int PAGE_SLOT = 30;
    public static final int NEXT_SLOT = 31;

    // Standard chest offsets: slot (row, col) → x = 8 + col*18, y = 18 + row*18
    private static final int TITLE_X = 80, TITLE_Y = 18; // row 0, col 4
    private static final int NAV_Y   = 108; // row 5
    private static final int PREV_X  = 8; //        col 0
    private static final int PAGE_X  = 80; //        col 4
    private static final int NEXT_X  = 152; //        col 8

    private final SimpleContainer container;

    // Server-side only
    @Nullable private List<MoveTemplate> allMoves;
    @Nullable private Pokemon selectedPokemon;
    @Nullable private String type;
    private int currentPage = 0;

    @SuppressWarnings("unused")
    public SelectMoveMenu(int containerID, Inventory inventory,
                          ServerPlayer player, @NotNull Pokemon pokemon, @NotNull String type) {
        super(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), containerID);
        this.selectedPokemon = pokemon;
        this.type = type;
        this.allMoves = new ArrayList<>(PokemonUtility.getFilteredMoves(pokemon, getTutorConfig()));
        this.container = new SimpleContainer(32);
        addDisplaySlots();
        populatePage(0);
    }

    @SuppressWarnings("unused")
    public SelectMoveMenu(int containerID, Inventory inventory) {
        super(MenuTypesRegistry.SELECT_MOVE_SCREEN.get(), containerID);
        this.container = new SimpleContainer(32);
        addDisplaySlots();
    }

    private void addDisplaySlots() {
        // Title slot
        addSlot(new NoHighlightButton(container, TITLE_SLOT, TITLE_X, TITLE_Y));

        // Move slots: rows 1-4, cols 1-7  →  28 display slots
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 7; col++) {
                int si = (row - 1) * 7 + (col - 1) + FIRST_MOVE_SLOT;
                int x  = 8 + col * 18;
                int y  = 18 + row * 18;
                addSlot(new Button(container, si, x, y));
            }
        }

        // Navigation slots (row 5)
        addSlot(new Button(container, PREV_SLOT, PREV_X, NAV_Y));
        addSlot(new NoHighlightButton(container, PAGE_SLOT, PAGE_X, NAV_Y));
        addSlot(new Button(container, NEXT_SLOT, NEXT_X, NAV_Y));
    }

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
                        StaticButtons.getMoveItem(allMoves.get(moveIdx), selectedPokemon));
            }
        }
        // Update nav + title items
        container.setItem(TITLE_SLOT, StaticButtons.buildTitleItem(ModelData.GUI_TEXT.SELECT_MOVE));
        container.setItem(PREV_SLOT, StaticButtons.buildPrevItem());
        container.setItem(PAGE_SLOT, StaticButtons.buildPageItem(page + 1, totalPages));
        container.setItem(NEXT_SLOT, StaticButtons.buildNextItem());
    }

    private int getTotalPages() {
        if (allMoves != null) {
            return Math.max(1, (allMoves.size() + PAGE_SIZE - 1) / PAGE_SIZE);
        }
        return 1;
    }

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
                ItemStack moveItem = StaticButtons.getMoveItem(move, selectedPokemon);
                TutorMenuProvider.open.confirmationMenu(sp, moveItem,
                        () -> MoveManager.learnMove(sp, selectedPokemon, move,
                                () -> TutorMenuProvider.open.selectMoveMenu(sp, selectedPokemon, type)),
                        () -> TutorMenuProvider.open.selectMoveMenu(sp, selectedPokemon, type)
                );
            }
        }
    }

    private CommonConfig.TutorConfig getTutorConfig() {
        if (type == null || type.isEmpty() || !type.equals("admin")) {
            return CobblemonMoveTutorCommon.getCommonConfig().villageTutorConfig;
        }
        return CobblemonMoveTutorCommon.getCommonConfig().adminTutorConfig;
    }
}





