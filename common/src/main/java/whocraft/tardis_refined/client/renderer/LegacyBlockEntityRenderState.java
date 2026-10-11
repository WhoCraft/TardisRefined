package whocraft.tardis_refined.client.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;

/** Temporary extraction payload used while individual renderers move to immutable state records. */
public final class LegacyBlockEntityRenderState<T extends BlockEntity> extends BlockEntityRenderState {
    public T blockEntity;
    public float partialTick;
}
