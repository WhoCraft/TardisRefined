package whocraft.tardis_refined.mixin.forge;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import whocraft.tardis_refined.client.ZeitonGlassTracker;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements IBlockEntityExtension {

    @Override
    public void onLoad() {
        if ((Object) this instanceof ZeitonGlassBlockEntity zeitonGlassBlockEntity) {
            ZeitonGlassTracker.onLoad(zeitonGlassBlockEntity);
        }
    }


}
