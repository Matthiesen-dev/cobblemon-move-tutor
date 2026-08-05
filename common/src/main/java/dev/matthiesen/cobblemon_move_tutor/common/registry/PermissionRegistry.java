package dev.matthiesen.cobblemon_move_tutor.common.registry;

import dev.matthiesen.cobblemon_move_tutor.common.CobblemonMoveTutor;
import dev.matthiesen.cobblemon_move_tutor.common.config.MoveTutorConfig;
import dev.matthiesen.matthiesen_core.common.api.permissions.Permission;
import dev.matthiesen.matthiesen_core.common.api.permissions.PermissionLevel;
import dev.matthiesen.matthiesen_core.common.utility.AbstractPermission;
import net.minecraft.commands.CommandSourceStack;

public final class PermissionRegistry {
    public static Permission MOVE_TUTOR_PERMISSION = register("command.move-tutor",
            MoveTutorConfig.PERMISSIONS_CONFIG.permissionLevels_cmdMoveTutor.getAsInt());
    public static Permission MOVE_TUTOR_OTHER_PERMISSION = register("command.move-tutor.other",
            MoveTutorConfig.PERMISSIONS_CONFIG.permissionLevels_cmdMoveTutor_other.getAsInt());

    public static class Permissions {
        public Permission MOVE_TUTOR_PERMISSION = PermissionRegistry.MOVE_TUTOR_PERMISSION;
        public Permission MOVE_TUTOR_OTHER_PERMISSION = PermissionRegistry.MOVE_TUTOR_OTHER_PERMISSION;
    }

    public static Permissions getPermissions() {
        return new Permissions();
    }

    public static void init() {}

    public static boolean checkPermission(CommandSourceStack source, Permission permission) {
        return CobblemonMoveTutor.INSTANCE.getPermissionsManager().getPermissionValidator().hasPermission(source, permission);
    }

    public static PermissionLevel toPermLevel(int permLevel) {
        for (PermissionLevel value : PermissionLevel.values()) {
            if (value.ordinal() == permLevel) {
                return value;
            }
        }
        return PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS;
    }

    private static Permission register(String node, int level) {
        var newPermission = modPermission(node, toPermLevel(level));
        CobblemonMoveTutor.INSTANCE.getPermissionsManager().registerPermission(newPermission);
        return newPermission;
    }

    private static Permission modPermission(String node, PermissionLevel level) {
        return new AbstractPermission(node, level) {
            @Override
            protected String getModId() {
                return CobblemonMoveTutor.MOD_ID;
            }

            @Override
            protected String getPermissionNamespace() {
                return "CobblemonMoveTutor";
            }
        };
    }
}
