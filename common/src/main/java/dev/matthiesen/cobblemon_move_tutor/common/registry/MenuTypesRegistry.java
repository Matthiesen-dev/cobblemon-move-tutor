package dev.matthiesen.cobblemon_move_tutor.common.registry;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.ui.ConfirmationMenu;
import dev.matthiesen.cobblemon_move_tutor.common.ui.PokemonSelectionMenu;
import dev.matthiesen.cobblemon_move_tutor.common.ui.SelectMoveMenu;
import dev.matthiesen.cobblemon_move_tutor.common.ui.TutorMenu;
import dev.matthiesen.matthiesen_core.common.registry.AbstractMenuTypeRegistry;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public final class MenuTypesRegistry extends AbstractMenuTypeRegistry {
    private static final MenuTypesRegistry INSTANCE = new MenuTypesRegistry();

    public MenuTypesRegistry() {
        super(CobblemonMoveTutor.MOD_ID);
    }

    public static void init() {}

    public static Supplier<MenuType<ConfirmationMenu>> CONFIRMATION_SCREEN;
    public static Supplier<MenuType<PokemonSelectionMenu>> POKEMON_SELECTION_SCREEN;
    public static Supplier<MenuType<SelectMoveMenu>> SELECT_MOVE_SCREEN;

    static {
        CONFIRMATION_SCREEN = INSTANCE.register("confirmation_screen", TutorMenu::confirmationMenu);
        POKEMON_SELECTION_SCREEN = INSTANCE.register("pokemon_selection_screen", TutorMenu::pokemonSelectionMenu);
        SELECT_MOVE_SCREEN = INSTANCE.register("select_move_screen", TutorMenu::selectMoveMenu);
    }
}
