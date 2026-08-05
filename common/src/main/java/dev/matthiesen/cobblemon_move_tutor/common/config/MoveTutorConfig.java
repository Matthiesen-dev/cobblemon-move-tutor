package dev.matthiesen.cobblemon_move_tutor.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class MoveTutorConfig {
    public static final CommonServerConfig SERVER_CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    public static final PermissionsConfig PERMISSIONS_CONFIG;
    public static final ModConfigSpec PERMISSIONS_SPEC;

    static {
        Pair<CommonServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(CommonServerConfig::new);
        SERVER_CONFIG = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();

        Pair<PermissionsConfig, ModConfigSpec> permissionsPair = new ModConfigSpec.Builder().configure(PermissionsConfig::new);
        PERMISSIONS_CONFIG = permissionsPair.getLeft();
        PERMISSIONS_SPEC = permissionsPair.getRight();
    }

    public record TutorConfig(
            boolean hideAlreadyKnownMoves,
            boolean levelMove,
            boolean tmMove,
            boolean legacyMove,
            boolean tutorMove,
            boolean specialMove,
            boolean eggMove
    ) {
        public static TutorConfig villageTutorConfig() {
            return new TutorConfig(
                    SERVER_CONFIG.village_hideAlreadyKnownMoves.get(),
                    SERVER_CONFIG.village_levelMove.get(),
                    SERVER_CONFIG.village_tmMove.get(),
                    SERVER_CONFIG.village_legacyMove.get(),
                    SERVER_CONFIG.village_tutorMove.get(),
                    SERVER_CONFIG.village_specialMove.get(),
                    SERVER_CONFIG.village_eggMove.get()
            );
        }

        public static TutorConfig adminTutorConfig() {
            return new TutorConfig(
                    SERVER_CONFIG.admin_hideAlreadyKnownMoves.get(),
                    SERVER_CONFIG.admin_levelMove.get(),
                    SERVER_CONFIG.admin_tmMove.get(),
                    SERVER_CONFIG.admin_legacyMove.get(),
                    SERVER_CONFIG.admin_tutorMove.get(),
                    SERVER_CONFIG.admin_specialMove.get(),
                    SERVER_CONFIG.admin_eggMove.get()
            );
        }
    }
}
