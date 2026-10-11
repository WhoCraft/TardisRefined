package whocraft.tardis_refined.client.renderer.blockentity.shell;

import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderer;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.TRShaders;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.patterns.ShellPattern;

public class GlobalShellRenderer extends LegacyBlockEntityRenderer<GlobalShellBlockEntity> {


    public GlobalShellRenderer(Context context) {
        super(context);
    }

    @Override
    protected void renderLegacy(GlobalShellBlockEntity blockEntity, float partialTick, PoseStack poseStack, DeferredGeometryBuffer bufferSource, int packedLight) {
        if (blockEntity.getTardisId() != null && Minecraft.getInstance().player != null) {
            TardisClientData flight = TardisClientData.getInstance(blockEntity.getTardisId());
            boolean activeFlight = flight != null
                    && (flight.isTakingOff() || flight.isFlying() || flight.isLanding());
            boolean shellView = TardisPlayerInfo.get(Minecraft.getInstance().player)
                    .map(TardisPlayerInfo::isViewingTardis).orElse(false);
            if (activeFlight && shellView) {
                // The 1.20.2 shell view rendered one animated shell inside the
                // vortex scene. 26.3 keeps the exterior world behind its PIP,
                // so also drawing this block entity creates a second TARDIS.
                // The PIP copy uses the same model and synchronized animation.
                ShellPreviewDiagnostics.reportEvery("physical-shell-hidden-in-vortex-view",
                        "tardis=" + blockEntity.getTardisId().identifier()
                                + ", takingOff=" + flight.isTakingOff()
                                + ", flying=" + flight.isFlying()
                                + ", landing=" + flight.isLanding(), 1000);
                return;
            }
        }
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(RootedShellBlock.FACING).toYRot();
        poseStack.rotateDegrees(Axis.YP, rotation);
        Identifier theme = blockEntity.theme();
        ShellPattern pattern = blockEntity.pattern();
        boolean isOpen = blockstate.getValue(GlobalShellBlock.OPEN);

        float sine = 0;
        if (blockstate.getValue(ShellBaseBlock.REGEN)) {
            sine = (float) ((Math.sin(0.1 * blockEntity.getLevel().getGameTime()) * 1));
            if (sine < 0) {
                sine = 0;
            }
        }

        var currentModel = ShellModelCollection.getInstance().getShellEntry(theme).getShellModel(pattern);

        ClientLevel level = Minecraft.getInstance().level;
        BlockPos blockPos = blockEntity.getBlockPos();
        Biome biome = level.getBiome(blockPos).value();
        Biome.Precipitation precipitation = biome.getPrecipitationAt(blockPos, level.getSeaLevel());
        boolean renderSnow = biome.hasPrecipitation() && precipitation == Biome.Precipitation.SNOW;

        currentModel.renderShell(blockEntity, isOpen, true, poseStack, bufferSource.getBuffer(TRShaders.translucentWithSnow(currentModel.getShellTexture(pattern, false), renderSnow)), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        /*Emmissive*/
        Boolean isRegenerating = blockstate.getValue(ShellBaseBlock.REGEN);
        if (pattern.shellTexture().emissive()) {
            RenderType glowingRenderType = TRShaders.glow(currentModel.getShellTexture(pattern, true), 1F);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(glowingRenderType);
            currentModel.renderShell(blockEntity, isOpen, false, poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, (isRegenerating) ? sine : 1f);
        } else {
            if (isRegenerating) {
                RenderType glowingRenderTypeRegen = TRShaders.glow(currentModel.getShellTexture(pattern, false), 1F);
                VertexConsumer vertexConsumerRegen = bufferSource.getBuffer(glowingRenderTypeRegen);
                currentModel.renderShell(blockEntity, isOpen, false, poseStack, vertexConsumerRegen, 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
            }
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public boolean shouldRender(GlobalShellBlockEntity blockEntity, Vec3 vec3) {
        return true;
    }



}
