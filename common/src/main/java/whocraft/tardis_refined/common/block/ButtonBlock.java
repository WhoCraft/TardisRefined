package whocraft.tardis_refined.common.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class ButtonBlock extends net.minecraft.world.level.block.ButtonBlock {
    public ButtonBlock(boolean bl, Properties properties) {
        super(bl, properties);
    }

    @Override
    protected SoundEvent getSound(boolean bl) {
        return bl ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }
}
