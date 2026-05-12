package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.cobblemon_move_tutor.ui.ConfirmationMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.PokemonSelectionMenu;
import dev.matthiesen.common.cobblemon_move_tutor.ui.SelectMoveMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MenuTypesRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Constants.MOD_ID, Registries.MENU);

    public static RegistrySupplier<MenuType<ConfirmationMenu>> CONFIRMATION_SCREEN =
            register("confirmation_screen", () -> new MenuType<>(ConfirmationMenu::new, FeatureFlagSet.of()));

    public static RegistrySupplier<MenuType<PokemonSelectionMenu>> POKEMON_SELECTION_SCREEN =
            register("pokemon_selection_screen", () -> new MenuType<>(PokemonSelectionMenu::new, FeatureFlagSet.of()));

    public static RegistrySupplier<MenuType<SelectMoveMenu>> SELECT_MOVE_SCREEN =
            register("select_move_screen", () -> new MenuType<>(SelectMoveMenu::new, FeatureFlagSet.of()));

    public static void init() {
        MENU_TYPES.register();
    }

    public static <T extends MenuType<?>> RegistrySupplier<T> register(String name, Supplier<T> menuType) {
        return MENU_TYPES.register(Constants.modResource(name), menuType);
    }
}
