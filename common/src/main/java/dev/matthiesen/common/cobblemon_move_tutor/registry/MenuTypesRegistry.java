package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.*;
import dev.matthiesen.common.matthiesen_lib.registry.AbstractMenuTypeRegistry;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MenuTypesRegistry extends AbstractMenuTypeRegistry {
    private static final MenuTypesRegistry INSTANCE = new MenuTypesRegistry();

    protected MenuTypesRegistry() {
        super(Constants.MOD_ID);
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
