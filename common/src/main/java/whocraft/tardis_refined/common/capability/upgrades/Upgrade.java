package whocraft.tardis_refined.common.capability.upgrades;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Upgrade {

    public static final CustomRegistry<Upgrade> UPGRADES = CustomRegistry.create(Upgrade.class, new ResourceLocation(TardisRefined.MODID, "upgrades"));

    private final Supplier<Upgrade> parent;
    private int cost = 1;
    private boolean posSet = false;
    private int posX = 0, posY = 0;

    public Upgrade() {
        this.parent = null;
    }

    public Upgrade(Supplier<Upgrade> parent) {
        this.parent = parent;
    }

    public Upgrade setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public int getCost() {
        return this.cost;
    }

    public Upgrade setPosition(int x, int y) {
        this.posSet = true;
        this.posX = x;
        this.posY = y;
        return this;
    }

    @Nullable
    public Vec2 getScreenPosition() {
        if (!this.posSet) {
            return null;
        }
        return new Vec2(this.posX, this.posY);
    }

    @Nullable
    public Upgrade getParent() {
        return this.parent != null ? this.parent.get() : null;
    }

    public List<Upgrade> getDirectChildren() {
        List<Upgrade> upgrades = new ArrayList<>();

        for (Upgrade upgrade : UPGRADES.getValues()) {
            if (upgrade.getParent() == this) {
                upgrades.add(upgrade);
            }
        }

        return upgrades;
    }

    public Component getDisplayName() {
        return Component.translatable(Util.makeDescriptionId("upgrade", getKey()));
    }

    public Component getDisplayDescription() {
        return Component.translatable(Util.makeDescriptionId("upgrade", getKey()) + ".description");
    }

    public void onUnlocked(TardisLevelOperator tardisLevelOperator, UpgradeHandler upgradeHandler) {
    }

    public void onLocked(TardisLevelOperator tardisLevelOperator, UpgradeHandler upgradeHandler) {
    }

    public boolean isUnlocked(UpgradeHandler upgradeHandler) {
        if (this.cost <= 0) {
            return true;
        }
        return upgradeHandler.isUpgradeUnlocked(this);
    }

    public ResourceLocation getKey() {
        return UPGRADES.getKey(this);
    }
}
