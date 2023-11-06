package whocraft.tardis_refined.common.capability.upgrades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpgradeHandler {

    // TODO max xp per point might change, set to 50 for testing
    public static final int XP_PER_UPGRADE_POINT = 50;

    @NotNull
    private final TardisLevelOperator tardisLevelOperator;
    private int upgradeXP = 0;
    private int upgradePoints = 0;
    private final List<Upgrade> unlockedUpgrades = new ArrayList<>();

    public UpgradeHandler(@NotNull TardisLevelOperator tardisLevelOperator) {
        this.tardisLevelOperator = tardisLevelOperator;
    }

    public void setUpgradeXP(int upgradeXP) {
        this.upgradeXP = Mth.clamp(upgradeXP, 0, 49);
    }

    public List<Upgrade> getUnlockedUpgrades() {
        return this.unlockedUpgrades;
    }

    public int getUpgradeXP() {
        return this.upgradeXP;
    }

    public void addUpgradeXP(int upgradeXP) {
        this.upgradeXP += upgradeXP;

        while (this.upgradeXP >= XP_PER_UPGRADE_POINT) {
            this.upgradePoints++;
            this.upgradeXP -= XP_PER_UPGRADE_POINT;
        }

        while (this.upgradeXP <= -XP_PER_UPGRADE_POINT) {
            this.upgradePoints--;
            this.upgradeXP += XP_PER_UPGRADE_POINT;
        }

        if (this.upgradeXP < 0) {
            this.upgradePoints--;
            this.upgradeXP = XP_PER_UPGRADE_POINT - Mth.abs(this.upgradeXP);
        }

        this.upgradePoints = Mth.clamp(this.upgradePoints, 0, 99);

    }

    public int getNeededXPForNextPoint() {
        return XP_PER_UPGRADE_POINT - this.upgradeXP;
    }

    public void setUpgradePoints(int upgradePoints) {
        this.upgradePoints = Mth.clamp(upgradePoints, 0, 99);

    }

    public int getUpgradePoints() {
        return this.upgradePoints;
    }

    public void addUpgradePoints(int upgradePoints) {
        this.setUpgradePoints(this.getUpgradePoints() + upgradePoints);
    }

    public boolean isUpgradeUnlocked(Upgrade upgrade) {
        Upgrade s = upgrade;

        while (s != null) {
            if (!this.unlockedUpgrades.contains(upgrade)) {
                return false;
            }

            s = s.getParent();
        }

        return true;
    }

    public void unlockUpgrade(Upgrade upgrade) {
        if (!this.isUpgradeUnlocked(upgrade)) {
            var parent = upgrade.getParent();

            while (parent != null) {
                if (!this.isUpgradeUnlocked(parent)) {
                    this.unlockedUpgrades.add(parent);
                    parent.onUnlocked(this.tardisLevelOperator, this);
                    parent = parent.getParent();
                } else {
                    break;
                }
            }

            this.unlockedUpgrades.add(upgrade);
            upgrade.onUnlocked(this.tardisLevelOperator, this);
        }
    }

    public void lockUpgrade(Upgrade upgrade) {
        if (this.unlockedUpgrades.contains(upgrade)) {
            this.unlockedUpgrades.remove(upgrade);
            upgrade.onLocked(this.tardisLevelOperator, this);
            for (Upgrade child : upgrade.getDirectChildren()) {
                this.lockUpgrade(child);
            }
        }
    }

    public CompoundTag toNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("UpgradeXP", this.upgradeXP);
        compoundTag.putInt("UpgradePoints", this.upgradePoints);

        ListTag unlockedUpgradesTag = new ListTag();
        for (Upgrade upgrade : this.unlockedUpgrades) {
            unlockedUpgradesTag.add(StringTag.valueOf(Objects.requireNonNull(Upgrade.UPGRADES.getKey(upgrade)).toString()));
        }
        compoundTag.put("UnlockedUpgrades", unlockedUpgradesTag);

        return compoundTag;
    }

    public void fromNBT(CompoundTag nbt) {
        this.upgradeXP = nbt.getInt("UpgradeXP");
        this.upgradePoints = nbt.getInt("UpgradePoints");
        this.unlockedUpgrades.clear();
        for (Tag upgrade : nbt.getList("UnlockedUpgrades", StringTag.TAG_STRING)) {
            this.unlockedUpgrades.add(Upgrade.UPGRADES.get(new ResourceLocation(upgrade.getAsString())));
        }
    }
}