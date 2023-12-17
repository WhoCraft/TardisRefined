package whocraft.tardis_refined.compat.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.block.display.AllDisplayBehaviours;
import com.simibubi.create.content.logistics.block.display.target.SignDisplayTarget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.TardisRefined;

import static whocraft.tardis_refined.registry.BlockEntityRegistry.FLIGHT_DETECTOR;

public class CreateInit {

    public static void init(){
        AllDisplayBehaviours.assignTile(AllDisplayBehaviours.register(new ResourceLocation(TardisRefined.MODID, "tardis_flight"), new TardisDisplaySource()), FLIGHT_DETECTOR.get());
    }
}
