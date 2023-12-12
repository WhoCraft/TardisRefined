package whocraft.tardis_refined.common.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;

public class TardisHelper {

    public static void playCloisterBell(TardisLevelOperator tardisLevelOperator) {
        for (int i = 0; i < 3; i++) {
            tardisLevelOperator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1000f, 0.1f);
        }
    }
}
