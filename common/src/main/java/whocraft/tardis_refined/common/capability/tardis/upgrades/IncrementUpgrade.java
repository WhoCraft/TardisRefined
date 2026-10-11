package whocraft.tardis_refined.common.capability.tardis.upgrades;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class IncrementUpgrade extends Upgrade {

    int incrementAmount = 0;

    public IncrementUpgrade(Supplier<ItemStack> icon, Identifier translationKey, UpgradeType upgradeType) {
        super(icon, translationKey, upgradeType);
    }

    public IncrementUpgrade(Supplier<ItemStack> icon, Supplier<Upgrade> parent, Identifier translationKey, UpgradeType upgradeType) {
        super(icon, parent, translationKey, upgradeType);
    }

    public int getIncrementAmount() {
        return incrementAmount;
    }

    public IncrementUpgrade setIncrementAmount(int incrementAmount) {
        this.incrementAmount = incrementAmount;
        return this;
    }
}
