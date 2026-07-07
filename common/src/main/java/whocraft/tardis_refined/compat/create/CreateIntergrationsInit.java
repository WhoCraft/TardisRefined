package whocraft.tardis_refined.compat.create;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.registry.CreateRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.DeferredRegister;
import whocraft.tardis_refined.registry.RegistryHolder;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class CreateIntergrationsInit {

    public static final DeferredRegister<DisplaySource> DISPLAY_SOURCE_DEFERRED_REGISTRY =
            DeferredRegister.create(TardisRefined.MODID, CreateRegistries.DISPLAY_SOURCE);

    public static final RegistryHolder<DisplaySource, ?> BIG_DATA =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("tardis_bigdata", TardisDisplaySource::new);

    public static final RegistryHolder<DisplaySource, ?> FUEL =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("fuel", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.FUEL, (String.valueOf(Math.round(tardis.getPilotingManager().getFuelPercentage() * 100)))).append("%");
                        }
                        @Override
                        public ResourceLocation getId() {
                            return ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "fuel");
                        }
                    }));

    public static final RegistryHolder<DisplaySource, ?> DOOR_STATUS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("door", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.DOOR_STATUS, String.valueOf(tardis.getInternalDoor().isOpen()));
                        }
                        @Override
                        public ResourceLocation getId() {
                            return ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "door");
                        }
                    }));

    public static final RegistryHolder<DisplaySource, ?> LOCK_STATUS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("locked", () -> new QuickOneLineDisplaySource(
                    new QuickOneLineDisplaySource.TardisInfo() {
                        @Override
                        public MutableComponent provideInfo(TardisLevelOperator tardis) {
                            return Component.translatable(ModMessages.LOCK_STATUS, String.valueOf(tardis.getExteriorManager().locked()));
                        }
                        @Override
                        public ResourceLocation getId() {
                            return ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "locked");
                        }
                    }));

    public static final RegistryHolder<DisplaySource, ?> GPS =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("gps", () -> new TardisNavLocationDisplaySource(
                    new TardisNavLocationDisplaySource.TardisNavInfo() {
                        @Override
                        public TardisNavLocation provideInfo(TardisLevelOperator tardis) {
                            TardisNavLocation currentLoc = tardis.getPilotingManager().getCurrentLocation();
                            if (ModCompatChecker.valkyrienSkies() && Platform.getServer() != null) {
                                currentLoc = VSHelper.toWorldLocation(currentLoc);
                            }
                            return currentLoc;
                        }
                        @Override
                        public ResourceLocation getId() {
                            return ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "gps");
                        }
                    }));

    public static final RegistryHolder<DisplaySource, ?> DESTINATION =
            DISPLAY_SOURCE_DEFERRED_REGISTRY.register("destination", () -> new TardisNavLocationDisplaySource(
                    new TardisNavLocationDisplaySource.TardisNavInfo() {
                        @Override
                        public TardisNavLocation provideInfo(TardisLevelOperator tardis) {
                            return tardis.getPilotingManager().getTargetLocation();
                        }
                        @Override
                        public ResourceLocation getId() {
                            return ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "destination");
                        }
                    }));

    public static void init() {
        DISPLAY_SOURCE_DEFERRED_REGISTRY.register();
    }

    public static void initAssignments() {
        TardisRefined.LOGGER.info("Registering Create TARDIS Display Sources");

        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), BIG_DATA.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), FUEL.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), DOOR_STATUS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), LOCK_STATUS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), GPS.get());
        DisplaySource.BY_BLOCK.add(TRBlockRegistry.FLIGHT_DETECTOR.get(), DESTINATION.get());
    }
}