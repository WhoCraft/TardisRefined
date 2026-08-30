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

public class InitiativeConsoleModel extends HierarchicalModel implements ConsoleUnit {

    public static final AnimationDefinition IDLE = Frame.loadAnimation(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/initiative/idle.json"));
    public static final AnimationDefinition FLIGHT = Frame.loadAnimation(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/initiative/flight.json"));


    private static final ResourceLocation INITIATIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/console/initiative/initiative_console.png");
    private final ModelPart root;
    private final ModelPart throttle;
    private final ModelPart handbrake;
    private final ModelPart rotor_on;

    public InitiativeConsoleModel(ModelPart root) {
        this.root = root;
        this.throttle = Frame.findPart(this, "bone178");
        this.rotor_on = Frame.findPart(this, "rotor_on");
        this.handbrake = Frame.findPart(this, "bone185");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

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

        float rot = -1f + (2 * ((float) reactions.getThrottleStage() / TardisPilotingManager.MAX_THROTTLE_STAGE));
        throttle.xRot = rot;

        handbrake.xRot = reactions.isHandbrakeEngaged() ? 1f : 0f;

        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return INITIATIVE_TEXTURE;
    }

}