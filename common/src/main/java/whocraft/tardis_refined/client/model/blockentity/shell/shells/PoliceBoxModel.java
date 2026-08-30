package whocraft.tardis_refined.client.model.blockentity.shell.shells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.renderer.RenderHelper;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class PoliceBoxModel extends ShellModel {
    private final ModelPart root;
    private final ModelPart left_door;
    private final ModelPart right_door;
    private final ModelPart frame;

    public PoliceBoxModel(ModelPart root) {
        super(root);
        this.root = root;
        this.frame = root.getChild("tardis_frame");
        this.right_door = root.getChild("right_door");
        this.left_door = root.getChild("left_door");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.scale(1.05f, 1.05f, 1.05f);
        poseStack.translate(0, -0.07, 0);
        frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }

    @Override
    public void setDoorPosition(boolean open) {
        this.right_door.yRot = (open) ? -275f : 0;
    }

    @Override
    public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (isBaseModel) {
            poseStack.scale(1.05f, 1.05f, 1.05f);
            poseStack.translate(0, -0.07, 0);
        }
        int finalColor = RenderHelper.rgbaToInt(red, green, blue, getCurrentAlpha());
        handleAllAnimations(entity, frame, isBaseModel, open, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

        frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, finalColor);
        left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, finalColor);
        right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, finalColor);
    }


}