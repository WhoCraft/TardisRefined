package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityModelSet.class)
public interface EntityModelSetAccessor {
    @Accessor("roots") Map<ModelLayerLocation, LayerDefinition> tr$getRoots();

    @Mutable
    @Accessor("roots") void tr$setRoots(Map<ModelLayerLocation, LayerDefinition> roots);
}
