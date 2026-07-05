package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.jeryn.frame.tardis.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class CrystalConsoleModel extends HierarchicalModel implements ConsoleUnit {

    public static final AnimationDefinition IDLE = Frame.loadAnimation(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/crystal/idle.json"));
    public static final AnimationDefinition FLIGHT = Frame.loadAnimation(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/crystal/flight.json"));


    private static final ResourceLocation CRYSTAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/console/crystal/crystal_console.png");
    private final ModelPart base_control;
    private final ModelPart rotor;
    private final ModelPart rotor_purple;
    private final ModelPart controls;
    private final ModelPart spinninglight;
    private final ModelPart bb_main;
    private final ModelPart root;
    private final ModelPart throttle;
    private final ModelPart handbrake;

    public CrystalConsoleModel(ModelPart root) {
        this.root = root;
        this.base_control = root.getChild("base_control");
        this.rotor = root.getChild("rotor");
        this.rotor_purple = root.getChild("rotor_purple");
        this.controls = root.getChild("controls");
        this.spinninglight = root.getChild("spinninglight");
        this.bb_main = root.getChild("bb_main");
        this.throttle = Frame.findPart(this, "large_lever_control_throttle");
        this.handbrake = Frame.findPart(this, "large_lever3_control");
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        base_control.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rotor.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rotor_purple.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        controls.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        spinninglight.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }


    @Override
    public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, float partialTick, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root().getAllParts().forEach(ModelPart::resetPose);
        TardisClientData reactions = TardisClientData.getInstance(level.dimension());

        int playerTicks = Minecraft.getInstance().player.tickCount;
        float tickCount = playerTicks + partialTick;

        if (globalConsoleBlock != null && globalConsoleBlock.getBlockState().getValue(GlobalConsoleBlock.POWERED)) {
            if (reactions.isFlying()) {
                this.animate(reactions.ROTOR_ANIMATION, FLIGHT, tickCount);
            } else {
                if (TRConfig.CLIENT.PLAY_CONSOLE_IDLE_ANIMATIONS.get() && globalConsoleBlock != null) {
                    this.animate(globalConsoleBlock.liveliness, IDLE, tickCount);
                }
            }
        }


        float rot = -0.5f + (0.5f * ((float) reactions.getThrottleStage() / TardisPilotingManager.MAX_THROTTLE_STAGE));

        this.throttle.xRot = rot;

        this.handbrake.xRot = reactions.isHandbrakeEngaged() ? 0f : -0.5f;

        base_control.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rotor.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rotor_purple.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        controls.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        spinninglight.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);

    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return CRYSTAL_TEXTURE;
    }


    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }
}