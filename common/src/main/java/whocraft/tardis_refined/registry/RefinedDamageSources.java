package whocraft.tardis_refined.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import whocraft.tardis_refined.TardisRefined;

public class RefinedDamageSources {

    public static final ResourceKey<DamageType> EYE_OF_HARMONY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(TardisRefined.MODID, "eye_of_harmony"));

    public static DamageSource getSource(ServerLevel level, ResourceKey<DamageType> damageTypeResourceKey) {
        Holder.Reference<DamageType> damageType = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(damageTypeResourceKey);
        return new DamageSource(damageType);
    }

}
