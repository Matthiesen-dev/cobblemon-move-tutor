package dev.matthiesen.common.cobblemon_move_tutor.ui;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class TutorMenu<T extends AbstractContainerMenu> extends MenuType<T> {
    public TutorMenu(MenuSupplier<T> menuSupplier) {
        super(menuSupplier, FeatureFlagSet.of());
    }

    public static TutorMenu<ConfirmationMenu> confirmationMenu() {
        return new TutorMenu<>(ConfirmationMenu::new);
    }

    public static TutorMenu<PokemonSelectionMenu> pokemonSelectionMenu() {
        return new TutorMenu<>(PokemonSelectionMenu::new);
    }

    public static TutorMenu<SelectMoveMenu> selectMoveMenu() {
        return new TutorMenu<>(SelectMoveMenu::new);
    }
}
