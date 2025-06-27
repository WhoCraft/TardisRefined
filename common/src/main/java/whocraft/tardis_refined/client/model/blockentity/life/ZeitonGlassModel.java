package whocraft.tardis_refined.client.model.blockentity.life;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lwjgl.opengl.GL11;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.overlays.VortexOverlay;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

import static whocraft.tardis_refined.client.overlays.VortexOverlay.VORTEX;
import static whocraft.tardis_refined.client.renderer.vortex.RenderTargetHelper.*;

public class ZeitonGlassModel extends HierarchicalModel {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "zeitonglassmodel"), "main");
    private final ModelPart root;

    public ZeitonGlassModel(ModelPart root) {
        this.root = root.getChild("root");
    }


    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    public void renderToBuffer(ZeitonGlassBlockEntity blockEntity, PoseStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //root.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

           TardisClientData tardisClientData = TardisClientData.getInstance(blockEntity.getLevel().dimension());
           if (tardisClientData != null) {


               if(!getIsStencilEnabled(Minecraft.getInstance().getMainRenderTarget())){
                   setIsStencilEnabled(Minecraft.getInstance().getMainRenderTarget(), true);
               }

               stack.pushPose();

               // Fix transform
               stack.translate(0.5F, 1.5F, 0.5F);
               stack.mulPose(Axis.ZP.rotationDegrees(180F));
              // stack.mulPose(Axis.YP.rotationDegrees(rotation));
               stack.translate(0, 0, -0.01);

               RenderSystem.depthMask(true);

               // Render Door Frame
               MultiBufferSource.BufferSource imBuffer = stencilBufferStorage.getVertexConsumer();
              // currentModel.setDoorPosition(isOpen);
               //currentModel.renderFrame(blockEntity, isOpen, true, stack, imBuffer.getBuffer(RenderType.entityCutout(currentModel.getInteriorDoorTexture(blockEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

               // Finalize the current batch before changing rendering state


               // Enable and configure stencil buffer
               GL11.glEnable(GL11.GL_STENCIL_TEST);
               GL11.glStencilMask(0xFF);
               GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
               GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
               GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

               // Render portal mask with depth writing enabled
               RenderSystem.depthMask(true);
               stack.pushPose();
               root.render(stack, imBuffer.getBuffer(RenderType.entityTranslucentCull(new ResourceLocation(TardisRefined.MODID, "textures/black_portal.png"))), 0, OverlayTexture.NO_OVERLAY, 0, 0, 0, alpha);
               imBuffer.endBatch();
               stack.popPose();
               RenderSystem.depthMask(false);

               // Render vortex using stencil buffer
               GL11.glStencilMask(0x00);
               GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
               GlStateManager._depthFunc(GL11.GL_ALWAYS);

               GL11.glColorMask(true, true, true, false);
               stack.pushPose();
               stack.scale(10, 10, 10);

               VORTEX.time.speed = (0.3f + tardisClientData.getThrottleStage() * 0.1f);
               VORTEX.renderVortex(stack, 1, false);
               stack.popPose();

               GlStateManager._depthFunc(GL11.GL_LEQUAL);
               GL11.glColorMask(true, true, true, true);

               // Disable stencil test and restore state
               GL11.glDisable(GL11.GL_STENCIL_TEST);
               GL11.glStencilMask(0xFF);
               RenderSystem.depthMask(true);


               stack.popPose();
           }



    }


    @Override
    public ModelPart root() {
        return this.root;
    }


}