package dev.matthiesen.common.cobblemon_move_tutor.permissions;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.permission.PermissionLevel;
import dev.matthiesen.common.cobblemon_move_tutor.CobblemonMoveTutorCommon;
import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class ModPermissions {
    public final ModPermission MOVE_TUTOR_PERMISSION;
    public final ModPermission MOVE_TUTOR_OTHER_PERMISSION;

    public ModPermissions() {
        this.MOVE_TUTOR_PERMISSION = new ModPermission(
                Constants.MOD_ID + ".command.move-tutor",
                toPermLevel(CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_PERMISSION_LEVEL)
        );
        this.MOVE_TUTOR_OTHER_PERMISSION = new ModPermission(
                Constants.MOD_ID + ".command.move-tutor.other",
                toPermLevel(CobblemonMoveTutorCommon.getPermissionsConfig().permissionLevels.COMMAND_MOVE_TUTOR_OTHER_PERMISSION_LEVEL)
        );
    }

    public PermissionLevel toPermLevel(int permLevel) {
        for (PermissionLevel value : PermissionLevel.values()) {
            if (value.ordinal() == permLevel) {
                return value;
            }
        }
        return PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS;
    }

    public static boolean checkPermission(CommandSourceStack source, ModPermission permission) {
        return Cobblemon.INSTANCE.getPermissionValidator().hasPermission(source, permission);
    }
}