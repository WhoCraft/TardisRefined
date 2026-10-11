package whocraft.tardis_refined.common.capability.tardis.upgrades;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class SpeedUpgrade extends Upgrade {
    private int speedModifier = 1;

    public SpeedUpgrade(Supplier<ItemStack> icon, Identifier translationKey, UpgradeType upgradeType) {
        super(icon, translationKey, upgradeType);
    }

    public SpeedUpgrade(Supplier<ItemStack> icon, Supplier<Upgrade> parent, Identifier translationKey, UpgradeType upgradeType) {
        super(icon, parent, translationKey, upgradeType);
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public SpeedUpgrade setSpeedModifier(int speedModifier) {
        this.speedModifier = speedModifier;
        return this;
    }
}
