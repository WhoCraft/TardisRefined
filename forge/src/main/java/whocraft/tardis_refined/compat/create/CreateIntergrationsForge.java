package whocraft.tardis_refined.compat.create;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class CreateIntergrationsForge {

    /*public static final DeferredRegistry<DisplaySource> DISPLAY_SOURCE_DEFERRED_REGISTRY =
            DeferredRegistry.create(TardisRefined.MODID, CreateRegistries.DISPLAY_SOURCE);

    public static final RegistrySupplier<DisplaySource> BIG_DATA =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("tardis_bigdata", TardisDisplaySource::new);

    public static final RegistrySupplier<DisplaySource> FUEL =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("fuel", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.FUEL, (String.valueOf(Math.round(tardis.getPilotingManager().getFuelPercentage() * 100)))).append("%");
                        }
                        @Override
                        public ResourceLocation getId() {
                            return new ResourceLocation(TardisRefined.MODID, "fuel");
                        }
                    }));

    public static final RegistrySupplier<DisplaySource> DOOR_STATUS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("door", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.DOOR_STATUS, String.valueOf(tardis.getInternalDoor().isOpen()));
                        }
                        @Override
                        public ResourceLocation getId() {
                            return new ResourceLocation(TardisRefined.MODID, "door");
                        }
                    }));

    public static final RegistrySupplier<DisplaySource> LOCK_STATUS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("locked", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.LOCK_STATUS, String.valueOf(tardis.getExteriorManager().locked()));
                        }
                        @Override
                        public ResourceLocation getId() {
                            return new ResourceLocation(TardisRefined.MODID, "locked");
                        }
                    }));

    public static final RegistrySupplier<DisplaySource> GPS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("gps", () -> new TardisNavLocationDisplaySource(
                    new TardisNavLocationDisplaySource.TardisNavInfo() {
                        @Override
                        public TardisNavLocation provideInfo(TardisLevelOperator tardis) {
                            TardisNavLocation currentLoc = tardis.getPilotingManager().getCurrentLocation();
                            if (ModCompatChecker.valkyrienSkies()) {
                                currentLoc = VSHelper.toWorldLocation(currentLoc);
                            }
                            return currentLoc;
                        }
                        @Override
                        public ResourceLocation getId() {
                            return new ResourceLocation(TardisRefined.MODID, "gps");
                        }
                    }));

    public static final RegistrySupplier<DisplaySource> DESTINATION =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("destination", () -> new TardisNavLocationDisplaySource(
                    new TardisNavLocationDisplaySource.TardisNavInfo() {
                        @Override
                        public TardisNavLocation provideInfo(TardisLevelOperator tardis) {
                            return tardis.getPilotingManager().getTargetLocation();
                        }
                        @Override
                        public ResourceLocation getId() {
                            return new ResourceLocation(TardisRefined.MODID, "destination");
                        }
                    }));*/

    public static void init() {
        //DISPLAY_SOURCE_DEFERRED_REGISTRY.registerToModBus();
    }

    public static void initAssignments() {
        TardisRefined.LOGGER.info("Registering Create TARDIS Display Sources");

        /*DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), BIG_DATA.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), FUEL.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), DOOR_STATUS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), LOCK_STATUS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), GPS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), DESTINATION.get());*/
    }
}