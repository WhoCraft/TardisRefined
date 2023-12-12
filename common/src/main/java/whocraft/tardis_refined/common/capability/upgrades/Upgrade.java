package whocraft.tardis_refined.common.capability.upgrades;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Upgrade {


    private final Supplier<Upgrade> parent;
    private int cost = 1;
    private boolean posSet = false;
    private int posX = 0, posY = 0;
    private final Supplier<ItemStack> icon;

    private ResourceLocation translationKey;

    /**
     *
     * @param icon
     * @param translationKey - Requires the namespace of your mod (e.g. my_mod_id) and the registry object (E.g. explorer) to be used for display names and translation keys
     */
    public Upgrade(Supplier<ItemStack> icon, ResourceLocation translationKey) {
        this.icon = icon;
        this.parent = null;
        this.translationKey = translationKey;
    }

    /**
     *
     * @param icon
     * @param parent - the parent Upgrade object before we can unlock this current upgrade
     * @param translationKey - Requires the namespace of your mod (e.g. my_mod_id) and the registry object (E.g. explorer) to be used for display names and translation keys
     */
    public Upgrade(Supplier<ItemStack> icon, Supplier<Upgrade> parent, ResourceLocation translationKey) {
        this.icon = icon;
        this.parent = parent;
        this.translationKey = translationKey;
    }

    public ItemStack getIcon() {
        return this.icon.get();
    }

    public Upgrade setSkillPointsRequired(int cost) {
        this.cost = cost;
        return this;
    }

    /**
     * Currently, the Tardis has a XP system, every successful flight event supplies a certain amount of Tardis XP
     * 50 Tardis XP becomes 1 skill point
     * @return Skill points required before the upgrade unlocks
     */
    public int getSkillPointsRequired() {
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

        for (Upgrade upgrade : Upgrades.UPGRADE_REGISTRY.stream().toList()) {
            if (upgrade.getParent() == this) {
                upgrades.add(upgrade);
            }
        }

        return upgrades;
    }

    public Component getDisplayName() {
        return Component.translatable(Util.makeDescriptionId("upgrade", this.translationKey));
    }

    public Component getDisplayDescription() {
        return Component.translatable(Util.makeDescriptionId("upgrade", this.translationKey) + ".description");
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

}
