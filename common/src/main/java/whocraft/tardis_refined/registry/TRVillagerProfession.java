package whocraft.tardis_refined.registry;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.registries.Registries;

import net.minecraft.world.entity.npc.VillagerProfession;
import whocraft.tardis_refined.TardisRefined;


public class TRVillagerProfession {

    public static final DeferredRegistry<VillagerProfession> PROFESSIONS = DeferredRegistry.create(TardisRefined.MODID, Registries.VILLAGER_PROFESSION);

    public static final RegistrySupplier<VillagerProfession> PILOT = registerVillagerProfession("pilot", new VillagerProfession(
            "pilot", poiTypeHolder -> poiTypeHolder.value().is(TRPointOfInterestTypes.PILOT_POI), poiTypeHolder -> poiTypeHolder.value().equals(TRPointOfInterestTypes.PILOT_POI),
            ImmutableSet.of(), ImmutableSet.of(), TRSoundRegistry.DESTINATION_DING.get())
    );


    public static RegistrySupplier<VillagerProfession> registerVillagerProfession(String name, VillagerProfession villagerProfession) {
        return PROFESSIONS.register(name, () -> villagerProfession);
    }


}
