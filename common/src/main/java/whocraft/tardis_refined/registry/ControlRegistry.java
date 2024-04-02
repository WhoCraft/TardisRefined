package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.flight.*;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.tardis.control.ship.ToggleDoorControl;

public class ControlRegistry {
	/** Registry Key for the Controls registry. For addon mods, use this as the registry key*/
	public static final ResourceKey<Registry<Control>> CONTROL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "control"));

	/** Tardis Refined instance of the Controls registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
	public static final DeferredRegistry<Control> CONTROLS = DeferredRegistry.createCustom(TardisRefined.MODID, CONTROL_REGISTRY_KEY, true);

	/** Global instance of the Control custom registry created by Tardis Refined*/
	public static final Registry<Control> CONTROL_REGISTRY = CONTROLS.getRegistry();

	// Tardis refined controls
	public static final RegistrySupplier<Control> DOOR_TOGGLE = register(new ToggleDoorControl());
	public static final RegistrySupplier<Control> X = register(new CoordinateControl(CoordinateButton.X));
	public static final RegistrySupplier<Control> Y = register(new CoordinateControl(CoordinateButton.Y));
	public static final RegistrySupplier<Control> Z = register(new CoordinateControl(CoordinateButton.Z));
	public static final RegistrySupplier<Control> INCREMENT = register(new IncrementControl());
	public static final RegistrySupplier<Control> ROTATE = register(new RotationControl());
	public static final RegistrySupplier<Control> RANDOM = register(new RandomControl());
	public static final RegistrySupplier<Control> THROTTLE = register(new ThrottleControl());
	public static final RegistrySupplier<Control> MONITOR = register(new MonitorControl());
	public static final RegistrySupplier<Control> DIMENSION = register(new DimensionalControl());
	public static final RegistrySupplier<Control> FAST_RETURN = register(new FastReturnControl());
	public static final RegistrySupplier<Control> READOUT = register(new ReadoutControl());
	public static final RegistrySupplier<Control> GENERIC_NO_SHOW = register(new GenericControl());
	public static final RegistrySupplier<Control> HANDBRAKE = register(new HandbrakeControl());

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
		return CONTROLS.register(id, () -> control);
	}
}

