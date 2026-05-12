package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.PokemonSelectionMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.TutorMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MenuTypesRegistry {
    public static void init() {
    }

    public static Supplier<MenuType<ConfirmationMenu>> CONFIRMATION_SCREEN;
    public static Supplier<MenuType<PokemonSelectionMenu>> POKEMON_SELECTION_SCREEN;
    public static Supplier<MenuType<SelectMoveMenu>> SELECT_MOVE_SCREEN;

    static {
        CONFIRMATION_SCREEN = register("confirmation_screen", TutorMenu::confirmationMenu);
        POKEMON_SELECTION_SCREEN = register("pokemon_selection_screen", TutorMenu::pokemonSelectionMenu);
        SELECT_MOVE_SCREEN = register("select_move_screen", TutorMenu::selectMoveMenu);
    }

    public static <T extends MenuType<?>> Supplier<T> register(String name, Supplier<T> menuType) {
        return CobblemonMoveTutorCommon.COMMON_PLATFORM.registerMenuType(name, menuType);
    }
}
