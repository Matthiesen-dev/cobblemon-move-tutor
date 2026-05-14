package dev.matthiesen.common.cobblemon_move_tutor.registry;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
import dev.matthiesen.common.matthiesen_lib.interfaces.Permission;
import dev.matthiesen.common.matthiesen_lib.permission.AbstractPermission;
import dev.matthiesen.common.matthiesen_lib.permission.PermissionLevel;
import net.minecraft.commands.CommandSourceStack;

public class PermissionRegistry {
    public static Permission MOVE_TUTOR_PERMISSION = register("command.move-tutor",
            CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_PERMISSION_LEVEL);
    public static Permission MOVE_TUTOR_OTHER_PERMISSION = register("command.move-tutor.other",
            CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL);
    public static Permission MOVE_TUTOR_RELOAD_PERMISSION = register("command.move-tutor.reload",
            CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_RELOAD_PERMISSION_LEVEL);

    public static class Permissions {
        public Permission MOVE_TUTOR_PERMISSION = PermissionRegistry.MOVE_TUTOR_PERMISSION;
        public Permission MOVE_TUTOR_OTHER_PERMISSION = PermissionRegistry.MOVE_TUTOR_OTHER_PERMISSION;
        public Permission MOVE_TUTOR_RELOAD_PERMISSION = PermissionRegistry.MOVE_TUTOR_RELOAD_PERMISSION;
    }

    public static Permissions getPermissions() {
        return new Permissions();
    }

    public static void init() {}

    public static boolean checkPermission(CommandSourceStack source, Permission permission) {
        return MatthiesenLib.getPermissionValidator().hasPermission(source, permission);
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
        MatthiesenLib.registerPermission(newPermission);
        return newPermission;
    }

    private static Permission modPermission(String node, PermissionLevel level) {
        return new AbstractPermission(node, level) {
            @Override
            protected String getModId() {
                return Constants.MOD_ID;
            }

            @Override
            protected String getPermissionNamespace() {
                return "CobblemonPokeTotem";
            }
        };
    }
}
