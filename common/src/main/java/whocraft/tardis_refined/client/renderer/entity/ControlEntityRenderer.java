package whocraft.tardis_refined.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.EntityHitResult;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.compat.CuriosTrinketsUtil;

public class ControlEntityRenderer extends NoopRenderer<ControlEntity> {
    public ControlEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected boolean shouldShowName(ControlEntity entity, double distanceSquared) {
        return TRConfig.CLIENT.CONTROL_NAMES.get() && isMouseOverEntity(entity);
    }

    private boolean isMouseOverEntity(ControlEntity entity) {
        var hit = Minecraft.getInstance().hitResult;
        return hit instanceof EntityHitResult entityHit && entityHit.getEntity() == entity;
    }

    @Override
    public void extractRenderState(ControlEntity entity, EntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        var player = Minecraft.getInstance().player;
        if (player != null && !CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player).isEmpty()
                && entity.isTickingDown() && entity.level().getRandom().nextInt(20) == 0) {
            entity.level().addParticle(TRParticles.GALLIFREY.get(), entity.getRandomX(0.1),
                    entity.blockPosition().getY(), entity.getRandomZ(0.1), 0.0, 0.0, 0.0);
        }
    }
}
