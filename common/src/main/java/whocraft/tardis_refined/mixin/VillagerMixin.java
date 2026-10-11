package whocraft.tardis_refined.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRVillagerProfession;
import whocraft.tardis_refined.villager.FlyTardisAtPOI;
import whocraft.tardis_refined.villager.VillagerDuck;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

import java.util.UUID;

import static whocraft.tardis_refined.common.util.MiscHelper.convertTicksToRealTime;

@Mixin(Villager.class)
public class VillagerMixin implements VillagerDuck {

    @Unique
    private static final EntityDataAccessor<Integer> PILOT_TICKS = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.INT);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(PILOT_TICKS, 0);
    }

    @Inject(method = "addAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueOutput;)V", at = @At("TAIL"))
    public void addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        output.putInt("tardis_refined_pilot_ticks", tardisRefined$getPilotingTicks());
    }

    @Inject(method = "readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V", at = @At("TAIL"))
    public void readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        tardisRefined$setPilotingTicks(input.getIntOr("tardis_refined_pilot_ticks", 0));
    }


    @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        Villager villager = (Villager) (Object) this;
        if (villager.getVillagerData().profession().is(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.VILLAGER_PROFESSION, TRVillagerProfession.PILOT.getId()))) {

            // Handle Emerald
            if(player.getItemInHand(interactionHand).is(Items.EMERALD)){
                tardisRefined$setPilotingTicks(tardisRefined$getPilotingTicks() + FlyTardisAtPOI.EMERALD_FLIGHT_TIME);
                player.getItemInHand(interactionHand).shrink(1);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            if(tardisRefined$getPilotingTicks() > 0){
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.PILOT_TIME, convertTicksToRealTime(tardisRefined$getPilotingTicks())), true);
            } else {
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.DEMANDS_PAYMENT), true);
            }
            // Pilot time is purchased directly with emeralds, not through the normal
            // villager trading screen.
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        Villager villager = (Villager) (Object) this;

        if (villager.level() instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                TardisPilotingManager pilotingManager = tardisLevelOperator.getPilotingManager();

                if (pilotingManager.isCrashing()) {
                    if (!villager.getBrain().isActive(Activity.PANIC)) {
                        villager.getBrain().setActiveActivityIfPossible(Activity.PANIC);
                        return;
                    }
                }

                if (pilotingManager.isLanding()) {
                    if (!villager.getBrain().isActive(Activity.CELEBRATE)) {
                        villager.getBrain().setActiveActivityIfPossible(Activity.CELEBRATE);
                        return;
                    }
                }


                if (pilotingManager.isInFlight()) {
                    if (!villager.getBrain().isActive(Activity.WORK)) {
                        villager.getBrain().setDefaultActivity(Activity.WORK);
                        villager.getBrain().setActiveActivityIfPossible(Activity.WORK);
                        return;
                    }
                }

            });
        }
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tardisRefined$assignPilotProfession(CallbackInfo ci) {
        Villager villager = (Villager) (Object) this;
        if (villager.level() instanceof ServerLevel serverLevel
                && serverLevel.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
            TardisLevelOperator.get(serverLevel).ifPresent(operator -> {
                UUID pilotUuid = operator.getPilotVillagerUuid();
                if (pilotUuid != null) {
                    // The UUID is persisted with the TARDIS, but the pilot can
                    // die while the TARDIS remains loaded. Release that claim
                    // so the next villager can take over.
                    Villager currentPilot = serverLevel.getEntity(pilotUuid) instanceof Villager found ? found : null;
                    if (currentPilot == null || currentPilot.isRemoved() || currentPilot.isDeadOrDying()) {
                        operator.setPilotVillagerUuid(null);
                        pilotUuid = null;
                    }
                }
                if (pilotUuid == null) {
                    // The first villager to be ticked claims the single pilot role. The
                    // TARDIS operator persists this UUID so later spawns cannot duplicate it.
                    pilotUuid = villager.getUUID();
                    operator.setPilotVillagerUuid(pilotUuid);
                }

                if (villager.getUUID().equals(pilotUuid)) {
                    if (!villager.getVillagerData().profession().is(ResourceKey.create(Registries.VILLAGER_PROFESSION, TRVillagerProfession.PILOT.getId()))) {
                        villager.setVillagerData(villager.getVillagerData().withProfession(
                                BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(TRVillagerProfession.PILOT.get())));
                    }
                    // The pilot-specific work package replaces vanilla's
                    // workstation-acquisition behavior. Give it the active
                    // console as its job site so FlyTardisAtPOI can actually
                    // start and path to its stations.
                    GlobalConsoleBlockEntity console = operator.getPilotingManager().getCurrentConsole();
                    if (console != null && console.getBlockPos() != null) {
                        GlobalPos consoleJobSite = GlobalPos.of(serverLevel.dimension(), console.getBlockPos());
                        if (!villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).filter(consoleJobSite::equals).isPresent()) {
                            villager.getBrain().setMemory(MemoryModuleType.JOB_SITE, consoleJobSite);
                        }
                    }
                } else if (villager.getVillagerData().profession().is(ResourceKey.create(Registries.VILLAGER_PROFESSION, TRVillagerProfession.PILOT.getId()))) {
                    // Reconcile older saves that already contain multiple pilots.
                    ResourceKey<net.minecraft.world.entity.npc.villager.VillagerProfession> unemployed = ResourceKey.create(
                            Registries.VILLAGER_PROFESSION, Identifier.withDefaultNamespace("none"));
                    villager.setVillagerData(villager.getVillagerData().withProfession(
                            BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(
                                    BuiltInRegistries.VILLAGER_PROFESSION.getValue(unemployed.identifier()))));
                }
            });
        }
    }

    @Override
    public void tardisRefined$setPilotingTicks(int ticks) {
        Villager villager = (Villager) (Object) this;
        villager.getEntityData().set(PILOT_TICKS, ticks);
    }

    @Override
    public int tardisRefined$getPilotingTicks() {
        Villager villager = (Villager) (Object) this;
        return villager.getEntityData().get(PILOT_TICKS);
    }
}
