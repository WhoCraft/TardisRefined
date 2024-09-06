package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.flight.*;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.tardis.control.ship.ToggleDoorControl;

public class TRControlRegistry {
	/** Registry Key for the Controls registry. For addon mods, use this as the registry key*/
	public static final ResourceKey<Registry<Control>> CONTROL_REGISTRY_KEY = ResourceKey.createRegistryKey(TardisRefined.modLocation( "control"));

	/** Tardis Refined instance of the Controls registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
	public static final DeferredRegistry<Control> CONTROL_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, CONTROL_REGISTRY_KEY, true);

	/** Instance of registry containing all Control entries. Addon mod entries will be included in this registry as long as they are use the same ResourceKey<Registry<ObjectType>>. */
	public static final Registry<Control> CONTROL_REGISTRY = CONTROL_DEFERRED_REGISTRY.getRegistry().get();

	// Tardis refined controls
	public static final RegistrySupplier<Control> DOOR_TOGGLE = register(new ToggleDoorControl(TardisRefined.modLocation( "door_toggle")));
	public static final RegistrySupplier<Control> X = register(new CoordinateControl(CoordinateButton.X, TardisRefined.MODID));
	public static final RegistrySupplier<Control> Y = register(new CoordinateControl(CoordinateButton.Y, TardisRefined.MODID));
	public static final RegistrySupplier<Control> Z = register(new CoordinateControl(CoordinateButton.Z, TardisRefined.MODID));
	public static final RegistrySupplier<Control> INCREMENT = register(new IncrementControl(TardisRefined.modLocation( "increment")));
	public static final RegistrySupplier<Control> ROTATE = register(new RotationControl(TardisRefined.modLocation( "rotate")));
	public static final RegistrySupplier<Control> RANDOM = register(new RandomControl(TardisRefined.modLocation( "random")));
	public static final RegistrySupplier<Control> THROTTLE = register(new ThrottleControl(TardisRefined.modLocation( "throttle")));
	public static final RegistrySupplier<Control> MONITOR = register(new MonitorControl(TardisRefined.modLocation( "monitor")));
	public static final RegistrySupplier<Control> DIMENSION = register(new DimensionalControl(TardisRefined.modLocation( "dimension")));
	public static final RegistrySupplier<Control> FAST_RETURN = register(new FastReturnControl(TardisRefined.modLocation( "fast_return")));
	public static final RegistrySupplier<Control> READOUT = register(new ReadoutControl(TardisRefined.modLocation( "read_out")));
	public static final RegistrySupplier<Control> GENERIC_NO_SHOW = register(new GenericControl(TardisRefined.modLocation( "generic_no_show"), "control.tardis_refined.generic_control"));
	public static final RegistrySupplier<Control> HANDBRAKE = register(new HandbrakeControl(TardisRefined.modLocation( "hand_brake")));
	public static final RegistrySupplier<Control> FUEL = register(new FuelToggleControl(TardisRefined.modLocation( "fuel")));

	public static Control get(ResourceLocation id){
		Control potentialTheme = CONTROL_REGISTRY.get(id);
		if(potentialTheme != null){
			return potentialTheme;
		}
		return THROTTLE.get();
	}

	public static ResourceLocation getKey(Control control){
		return CONTROL_REGISTRY.getKey(control);
	}

	/**
	 * Register methods for Tardis Refined only
	 * @return the registered control
	 */
	private static RegistrySupplier<Control> register(Control control) {
		return register(control, control.getId().getPath());
	}
	private static RegistrySupplier<Control> register(Control control, String id) {
		return CONTROL_DEFERRED_REGISTRY.register(id, () -> control);
	}
}

