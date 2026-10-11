package whocraft.tardis_refined.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.registry.TRVillagerProfession;
import whocraft.tardis_refined.villager.FlyTardisAtPOI;

@Mixin(VillagerGoalPackages.class)
public class VillagerGoalPackagesMixin {

    @Inject(method = "getWorkPackage(Lnet/minecraft/core/Holder;F)Lcom/google/common/collect/ImmutableList;", at = @At("HEAD"), cancellable = true)
    private static void getWorkPackage(Holder<VillagerProfession> villagerProfession, float f, CallbackInfoReturnable<ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>>> cir) {

        if (villagerProfession.is(ResourceKey.create(Registries.VILLAGER_PROFESSION, TRVillagerProfession.PILOT.getId()))) {
            cir.setReturnValue(ImmutableList.of(Pair.of(5, new RunOne(ImmutableList.of(Pair.of(new FlyTardisAtPOI(), 7))))));
        }

    }

}
