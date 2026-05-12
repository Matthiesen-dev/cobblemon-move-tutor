package dev.matthiesen.common.cobblemon_move_tutor.ui;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import dev.architectury.registry.menu.MenuRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.ItemRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.registry.MenuTypesRegistry;
import dev.matthiesen.common.cobblemon_move_tutor.util.ItemBuilder;
import dev.matthiesen.common.cobblemon_move_tutor.util.ModelData;
import dev.matthiesen.common.cobblemon_move_tutor.util.PokemonUtility;
import dev.matthiesen.common.cobblemon_move_tutor.util.SoundsPlayer;
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

public class PokemonSelectionMenu extends AbstractContainerMenu {

    public static final int TITLE_SLOT        = 0;
    public static final int FIRST_POKEMON_SLOT = 1; // slots 1-6 → party indices 0-5

    // Title: row 0, col 4 → x = 8 + 4*18 = 80, y = 18
    private static final int TITLE_X  = 80;
    private static final int TITLE_Y  = 18;
    // Pokemon row: row 1, cols 1-3 and 5-7 (skip col 4 = centre gap) → y = 36
    private static final int[] POKEMON_X = { 26, 44, 62, 98, 116, 134 };
    private static final int   POKEMON_Y = 36;

    private final SimpleContainer container;

    // Server-side only
    @Nullable private Pokemon[] cachedParty;
    @Nullable private String type;

    public PokemonSelectionMenu(int containerID, Inventory inventory,
                                Pokemon[] party, String type) {
        super(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), containerID);
        this.cachedParty = party;
        this.type = type;
        this.container = new SimpleContainer(7);
        container.setItem(TITLE_SLOT, buildTitleItem());
        for (int i = 0; i < 6; i++) {
            container.setItem(FIRST_POKEMON_SLOT + i,
                    party[i] != null
                            ? PokemonUtility.pokemonToItem(party[i])
                            : PokemonUtility.getEmptyPokemonItem());
        }
        addDisplaySlots();
    }

    public PokemonSelectionMenu(int containerID, Inventory inventory) {
        super(MenuTypesRegistry.POKEMON_SELECTION_SCREEN.get(), containerID);
        this.container = new SimpleContainer(7);
        addDisplaySlots();
    }

    private void addDisplaySlots() {
        addSlot(new Slot(container, TITLE_SLOT, TITLE_X, TITLE_Y) {
            @Override public boolean mayPickup(Player p) { return false; }
            @Override public boolean mayPlace(ItemStack s) { return false; }
            @Override public boolean isHighlightable() { return false; }
        });
        for (int i = 0; i < 6; i++) {
            final int si = FIRST_POKEMON_SLOT + i;
            addSlot(new Slot(container, si, POKEMON_X[i], POKEMON_Y) {
                @Override public boolean mayPickup(Player p) { return false; }
                @Override public boolean mayPlace(ItemStack s) { return false; }
            });
        }
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!(player instanceof ServerPlayer sp)) return;
        if (slotId < FIRST_POKEMON_SLOT || slotId > 6) return;

        int partyIdx = slotId - FIRST_POKEMON_SLOT;
        Pokemon pokemon = cachedParty != null ? cachedParty[partyIdx] : null;
        if (pokemon != null) {
            new SoundsPlayer(CobblemonSounds.POKEDEX_CLICK).play(sp);
            SelectMoveMenu.openFor(sp, pokemon, type);
        } else {
            new SoundsPlayer(CobblemonSounds.POKE_BALL_HIT).play(sp);
        }
    }

    public static void openFor(ServerPlayer player, String type) {
        PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon[] party = new Pokemon[6];
        for (int i = 0; i < 6; i++) party[i] = partyStore.get(i);

        MenuRegistry.openMenu(player, new SimpleMenuProvider(
                (id, inv, p) -> new PokemonSelectionMenu(id, inv, party, type),
                Component.empty()
        ));
    }

    private static ItemStack buildTitleItem() {
        return new ItemBuilder(ItemRegistry.GUI_ITEM.get())
                .setModelData(ModelData.GUI_TEXT.CHOOSE_POKEMON)
                .hideAdditional()
                .setCustomName(Component.literal(" "))
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

