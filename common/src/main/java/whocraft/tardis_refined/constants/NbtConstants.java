package whocraft.tardis_refined.constants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.Platform;

public class NbtConstants {

    // TARDIS Level Operator

    public static final String TARDIS_ID = "tardis_id";

    public static final String TARDIS_IS_SETUP = "has_setup";
    public static final String TARDIS_INTERNAL_DOOR_ID = "internal_door_id";
    public static final String TARDIS_INTERNAL_DOOR_POSITION = "internal_door_pos";


    // Interior Manager
    public static final String TARDIS_IM_IS_WAITING_TO_GENERATE = "im_waiting_to_generate";
    public static final String TARDIS_IM_PREPARED_THEME = "im_prepared_desktop_theme";
    public static final String TARDIS_IM_GENERATING_DESKTOP = "im_generating_desktop";
    public static final String TARDIS_IM_GENERATION_COOLDOWN = "im_generation_cooldown";
    public static final String TARDIS_IM_GENERATED_CORRIDORS = "im_generated_corridors";
    public static final String TARDIS_IM_AIRLOCK_CENTER = "im_generated_airlock_center";

    // External Readings Data
    public static final String TARDIS_EXT_CURRENT_THEME = "terd_current_theme";
    public static final String LOCKED = "terd_locked";

    // Internal Door
    public static final String DOOR_IS_MAIN_DOOR = "is_main_door";
    public static final String DOOR_ID = "door_id";
    public static final String DOOR_IS_OPEN = "is_open";

    // External Shell
    public static final String SHELL_THEME_ID = "shell_id";


    // Controls
    public static final String CONTROL_ID = "control_id";

    public static final String CONTROL_IS_IN_FLIGHT = "ctrl_is_in_flight";
    public static final String CONTROL_INCREMENT_INDEX = "ctrl_increment_index";
    public static final String CONTROL_CURRENT_EXT = "ctrl_current_ext";

    // Location Constant
    public static final String LOCATION_POSITION = "_location_position";
    public static final String LOCATION_ROTATION = "_location_rotation";
    public static final String LOCATION_DIMENSION_MODID = "_location_dimension_id";
    public static final String LOCATION_DIMENSION_PATH = "_location_dimension_path";


    public static TardisNavLocation getTardisNavLocation(CompoundTag tag, String prefix, TardisLevelOperator operator) {
        BlockPos position = NbtUtils.readBlockPos(tag.getCompound(prefix + NbtConstants.LOCATION_POSITION));
        Direction direction = Direction.from2DDataValue(tag.getInt(prefix + NbtConstants.LOCATION_ROTATION));
        String dimension_modid = tag.getString(prefix + NbtConstants.LOCATION_DIMENSION_MODID);
        String dimension_path = tag.getString(prefix + NbtConstants.LOCATION_DIMENSION_PATH);

        if (dimension_modid != null && dimension_path != null) {
            ServerLevel level = operator.getLevel().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(dimension_modid, dimension_path)));
            if (level != null) {
                return new TardisNavLocation(position, direction, level);
            }
        }
        return null;
    }

    public static void putTardisNavLocation(CompoundTag tag, String prefix, TardisNavLocation location) {
        tag.put(prefix + NbtConstants.LOCATION_POSITION, NbtUtils.writeBlockPos(location.position));
        tag.putInt(prefix + NbtConstants.LOCATION_ROTATION, location.rotation.get2DDataValue());

        if(location.level == null) return;

        tag.putString(prefix + NbtConstants.LOCATION_DIMENSION_MODID, location.level.dimension().location().getNamespace());
        tag.putString(prefix + NbtConstants.LOCATION_DIMENSION_PATH, location.level.dimension().location().getPath());
    }
}
