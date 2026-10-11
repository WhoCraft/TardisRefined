package whocraft.tardis_refined.client.renderer.vortex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals26.ImmersivePortalsClient;

import static whocraft.tardis_refined.client.overlays.VortexOverlay.VORTEX;

/** Global door geometry submitted through Minecraft's deferred 26.3 renderer. */
public final class RenderTargetHelper {
    private RenderTargetHelper() {}

    public static void renderVortex(GlobalDoorBlockEntity blockEntity, PoseStack stack,
                                    DeferredGeometryBuffer buffers, int packedLight) {
        BlockState state = blockEntity.getBlockState();
        TardisClientData data = TardisClientData.getInstance(blockEntity.getLevel().dimension());
        if (data == null) return;
        // The server's internal-door entity is updated when the shell changes,
        // but 26.3 can leave the client copy at its original Factory appearance.
        // The synced TARDIS appearance is authoritative for rendering this door.
        Identifier theme = data.getShellTheme();
        ShellPattern pattern = ShellPatterns.getPatternOrDefault(theme, data.getShellPattern());
        ShellDoorModel model = ShellModelCollection.getInstance().getShellEntry(theme)
                .getShellDoorModel(pattern);
        if (model == null) return;
        ShellPreviewDiagnostics.report("interior-door-render",
                "dimension=" + blockEntity.getLevel().dimension().identifier()
                        + ", entityTheme=" + blockEntity.theme() + "/" + blockEntity.pattern().id()
                        + ", renderedTheme=" + theme + "/" + pattern.id()
                        + ", model=" + model.getClass().getSimpleName()
                        + ", cachedShell=" + data.getShellTheme() + "/" + data.getShellPattern());
        VORTEX.vortexType = VortexRegistry.VORTEX_DEFERRED_REGISTRY.get(data.getVortex());
        boolean flying = data.isFlying() && TRConfig.CLIENT.RENDER_VORTEX_IN_DOOR.get()
                && !(ModCompatChecker.immersivePortals() && ImmersivePortalsClient.shouldStopRenderingInPortal());
        boolean open = state.getValue(InternalDoorBlock.OPEN);
        float rotation = state.getValue(InternalDoorBlock.FACING).toYRot();

        if (state.hasProperty(GlobalDoorBlock.OFFSET) && state.getValue(GlobalDoorBlock.OFFSET)) {
            Direction facing = state.getValue(InternalDoorBlock.FACING);
            switch (facing) {
                case NORTH -> stack.translate(-0.5, 0, 0);
                case SOUTH -> stack.translate(0.5, 0, 0);
                case EAST -> stack.translate(0, 0, -0.5);
                case WEST -> stack.translate(0, 0, 0.5);
            }
        }

        stack.pushPose();
        stack.translate(0.5F, 1.5F, 0.5F);
        stack.rotateDegrees(Axis.ZP, 180F);
        stack.rotateDegrees(Axis.YP, rotation);
        stack.translate(0, 0, -0.01);
        model.setDoorPosition(open);
        model.renderFrame(blockEntity, open, true, stack,
                buffers.getBuffer(RenderTypes.entityCutout(model.getInteriorDoorTexture(pattern))),
                packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        Identifier portalTexture = flying && VORTEX.vortexType != null
                ? VORTEX.vortexType.getTexture() : model.getInteriorDoorTexture(pattern);
        boolean shellView = Minecraft.getInstance().player != null
                && TardisPlayerInfo.get(Minecraft.getInstance().player)
                .map(TardisPlayerInfo::isViewingTardis).orElse(false);
        // Shell view replaces the world with its own flight scene. Rendering
        // the in-world door's atlas-backed vortex portal at the same time
        // leaked its purple mask panel into the GUI picture-in-picture.
        if (!shellView) {
            model.renderPortalMask(blockEntity, open, true, stack,
                    buffers.getBuffer(RenderTypes.entityTranslucent(portalTexture)),
                    packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
        stack.popPose();
    }
}
