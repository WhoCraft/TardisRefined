package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PictureInPictureRenderer.class)
public interface PictureInPictureRendererAccessor {
    @Accessor("projection")
    Projection tardisRefined$getProjection();

    @Accessor("projectionMatrixBuffer")
    ProjectionMatrixBuffer tardisRefined$getProjectionMatrixBuffer();
}
