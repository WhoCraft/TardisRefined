package whocraft.tardis_refined.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.client.renderer.vortex.RenderTargetHelper;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeBus {

    @SubscribeEvent
    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();

        if (stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        PoseStack matrices = event.getPoseStack();

        TardisClientData tardisClientData = TardisClientData.getInstance(world.dimension());

        matrices.pushPose();
        RenderTargetHelper.renderZeitonGlass(
                camera,
                ModelRegistry.zeitonGlassModel,
                matrices,
                mc.renderBuffers().bufferSource(),
                LightTexture.FULL_BLOCK,
                tardisClientData,
                true
        );
        matrices.popPose();
    }

    @SubscribeEvent
    public static void tickTARDIS(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        TardisClientLogic.tickClientData(Minecraft.getInstance());
    }

    @SubscribeEvent
    public static void onPunchBlock(PlayerInteractEvent.LeftClickBlock e) {
        if (e.getEntity().level().isClientSide) return;

        TardisPlayerInfo.get(e.getEntity()).ifPresent(tardisPlayerInfo -> {
            e.setCanceled(tardisPlayerInfo.isViewingTardis());
        });

    }



}
