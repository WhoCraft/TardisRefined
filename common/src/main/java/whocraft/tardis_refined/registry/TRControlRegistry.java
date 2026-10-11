package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ExteriorDisplayControl;
import whocraft.tardis_refined.common.tardis.control.flight.*;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.tardis.control.ship.ToggleDoorControl;

public class TRControlRegistry {
    /**
     * Registry Key for the Controls registry. For addon mods, use this as the registry key
     */
    public static final ResourceKey<Registry<Control>> CONTROL_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "control"));

    /**
     * Tardis Refined instance of the Controls registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only
     */
    public static final DeferredRegistry<Control> CONTROL_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, CONTROL_REGISTRY_KEY, true);


    // Tardis refined controls
    public static final RegistrySupplier<Control> DOOR_TOGGLE = register(new ToggleDoorControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "door_toggle")));
    public static final RegistrySupplier<Control> X = register(new CoordinateControl(CoordinateButton.X, TardisRefined.MODID));
    public static final RegistrySupplier<Control> Y = register(new CoordinateControl(CoordinateButton.Y, TardisRefined.MODID));
    public static final RegistrySupplier<Control> Z = register(new CoordinateControl(CoordinateButton.Z, TardisRefined.MODID));
    public static final RegistrySupplier<Control> INCREMENT = register(new IncrementControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "increment")));
    public static final RegistrySupplier<Control> ROTATE = register(new RotationControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "rotate")));
    public static final RegistrySupplier<Control> RANDOM = register(new RandomControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "random")));
    public static final RegistrySupplier<Control> THROTTLE = register(new ThrottleControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "throttle")));
    public static final RegistrySupplier<Control> MONITOR = register(new MonitorControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "monitor")));
    public static final RegistrySupplier<Control> DIMENSION = register(new DimensionalControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "dimension")));
    public static final RegistrySupplier<Control> FAST_RETURN = register(new FastReturnControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "fast_return")));
    public static final RegistrySupplier<Control> READOUT = register(new ReadoutControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "read_out")));
    public static final RegistrySupplier<Control> GENERIC_NO_SHOW = register(new GenericControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "generic_no_show"), "control.tardis_refined.generic_control"));
    public static final RegistrySupplier<Control> HANDBRAKE = register(new HandbrakeControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "hand_brake")));
    public static final RegistrySupplier<Control> FUEL = register(new FuelToggleControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "fuel")));
    public static final RegistrySupplier<Control> EXTERIOR_DISPLAY = register(new ExteriorDisplayControl(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "exterior_display")));

    public static Control get(Identifier id) {
        Control potentialTheme = CONTROL_DEFERRED_REGISTRY.get(id);
        if (potentialTheme != null) {
            return potentialTheme;
        }
        return THROTTLE.get();
    }

    public static Identifier getKey(Control control) {
        return CONTROL_DEFERRED_REGISTRY.getKey(control);
    }

    /**
     * Register methods for Tardis Refined only
     *
     * @return the registered control
     */
    private static RegistrySupplier<Control> register(Control control) {
        return register(control, control.getId().getPath());
    }

    private static RegistrySupplier<Control> register(Control control, String id) {
        return CONTROL_DEFERRED_REGISTRY.register(id, () -> control);
    }
}

