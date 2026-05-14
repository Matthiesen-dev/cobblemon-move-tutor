package dev.matthiesen.common.cobblemon_move_tutor.permissions;

import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib.MatthiesenLib;
import dev.matthiesen.common.matthiesen_lib.interfaces.Permission;
import dev.matthiesen.common.matthiesen_lib.permission.AbstractPermission;
import dev.matthiesen.common.matthiesen_lib.permission.PermissionLevel;
import net.minecraft.commands.CommandSourceStack;

public class ModPermissions {
    public final Permission MOVE_TUTOR_PERMISSION;
    public final Permission MOVE_TUTOR_OTHER_PERMISSION;
    public final Permission MOVE_TUTOR_RELOAD_PERMISSION;

    public ModPermissions() {
        this.MOVE_TUTOR_PERMISSION = register("command.move-tutor",
                CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_PERMISSION_LEVEL);
        this.MOVE_TUTOR_OTHER_PERMISSION = register("command.move-tutor.other",
                CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL);
        this.MOVE_TUTOR_RELOAD_PERMISSION = register("command.move-tutor.reload",
                CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_RELOAD_PERMISSION_LEVEL);
    }

    public PermissionLevel toPermLevel(int permLevel) {
        for (PermissionLevel value : PermissionLevel.values()) {
            if (value.ordinal() == permLevel) {
                return value;
            }
        }
        return PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS;
    }

    public static boolean checkPermission(CommandSourceStack source, Permission permission) {
        return MatthiesenLib.getPermissionValidator().hasPermission(source, permission);
    }

    private Permission register(String node, int level) {
        var newPermission = modPermission(node, toPermLevel(level));
        MatthiesenLib.registerPermission(newPermission);
        return newPermission;
    }

    private Permission modPermission(String node, PermissionLevel level) {
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