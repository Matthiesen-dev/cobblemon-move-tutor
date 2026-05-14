package dev.matthiesen.common.cobblemon_move_tutor.permissions;

import dev.matthiesen.common.cobblemon_move_tutor.Constants;
import dev.matthiesen.common.matthiesen_lib.permission.AbstractPermission;
import dev.matthiesen.common.matthiesen_lib.permission.PermissionLevel;

public class ModPermission extends AbstractPermission {
    @Override
    protected String getModId() {
        return Constants.MOD_ID;
    }

    @Override
    protected String getPermissionNamespace() {
        return "CobblemonPokeTotem";
    }

    public ModPermission(String node, PermissionLevel level) {
        super(node, level);
    }
}