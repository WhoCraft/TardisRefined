package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.jeryn.frame.tardis.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import whocraft.tardis_refined.client.model.TardisModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class RefurbishedConsoleModel extends TardisModel implements ConsoleUnit {

    private static final Identifier REFURBISHED_TEXTURE = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/console/refurbished/refurbished_console.png");
    public static final AnimationDefinition IDLE = Frame.loadAnimation(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/refurbished/idle.json"));
    public static final AnimationDefinition FLIGHT = Frame.loadAnimation(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "frame/console/refurbished/flight.json"));
    private final ModelPart root;
    private final ModelPart throttle;
    private final ModelPart handbrake;

    public RefurbishedConsoleModel(ModelPart root) {
        this.root = root;
        this.throttle = Frame.findPart(this, "throttle");
        this.handbrake = Frame.findPart(this, "bone309");

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.ARGB.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().getAllParts().forEach(ModelPart::resetPose);
        TardisClientData reactions = TardisClientData.getInstance(level.dimension());

        // Preserve the console's authored idle and flight motion. These clips
        // are part of the old Refurbished console behavior and were missing
        // from the migrated model, despite their animation resources existing.
        if (globalConsoleBlock != null && globalConsoleBlock.getBlockState().getValue(whocraft.tardis_refined.common.block.console.GlobalConsoleBlock.POWERED)) {
            int tickCount = Minecraft.getInstance().player.tickCount;
            if (reactions.isFlying()) {
                this.animate(reactions.ROTOR_ANIMATION, FLIGHT, tickCount);
            } else if (TRConfig.CLIENT.PLAY_CONSOLE_IDLE_ANIMATIONS.get()) {
                this.animate(globalConsoleBlock.liveliness, IDLE, tickCount);
            }
        }

        float rot = -1f + (2 * ((float) reactions.getThrottleStage() / TardisPilotingManager.MAX_THROTTLE_STAGE));
        throttle.xRot = rot;

        handbrake.xRot = reactions.isHandbrakeEngaged() ? 1f : 0f;

        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.ARGB.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public Identifier getDefaultTexture() {
        return REFURBISHED_TEXTURE;
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }
}
