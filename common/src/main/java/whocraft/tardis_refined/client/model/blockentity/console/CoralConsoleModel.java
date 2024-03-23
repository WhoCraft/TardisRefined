package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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

import java.util.List;
import java.util.Set;

public class CoralConsoleModel extends HierarchicalModel implements ConsoleUnit {

	private static final ResourceLocation CORAL_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/coral/coral_console.png");


	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(10f).looping()
			.addAnimation("bone233",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.96f, KeyframeAnimations.degreeVec(2.99989f, 0.02617f, -0.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.28f, KeyframeAnimations.degreeVec(-0.95f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone234",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.degreeVec(1.84997f, 0.01308f, 0.52534f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone236",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.96f, KeyframeAnimations.degreeVec(2.99989f, 0.02617f, -0.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.28f, KeyframeAnimations.degreeVec(-0.95f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone237",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.96f, KeyframeAnimations.degreeVec(1.24997f, 0.01308f, -0.24966f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone238",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.96f, KeyframeAnimations.degreeVec(2.99989f, 0.02617f, -0.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.28f, KeyframeAnimations.degreeVec(-0.95f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone239",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.96f, KeyframeAnimations.degreeVec(1.69997f, 0.01308f, -0.62466f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.76f, KeyframeAnimations.degreeVec(0.69497f, -0.00606f, 0.72496f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone284",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone285",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone288",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone290",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone293",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone295",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone298",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone300",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone303",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone305",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone308",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone310",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone59",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.44f, KeyframeAnimations.degreeVec(0f, 281.535f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 1440f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone62",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.8f, KeyframeAnimations.degreeVec(0f, 211.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.degreeVec(0f, 66.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.44f, KeyframeAnimations.degreeVec(0f, 158f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.degreeVec(0f, 72.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.04f, KeyframeAnimations.degreeVec(0f, -10.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.64f, KeyframeAnimations.degreeVec(0f, 10.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("increment",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.56f, KeyframeAnimations.degreeVec(0f, 294.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.48f, KeyframeAnimations.degreeVec(0f, 174f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.8f, KeyframeAnimations.degreeVec(0f, 219f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.88f, KeyframeAnimations.degreeVec(0f, -85f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone68",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.degreeVec(0f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7f, KeyframeAnimations.degreeVec(0f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone83",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(0.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.16f, KeyframeAnimations.degreeVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.84f, KeyframeAnimations.degreeVec(0.25f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.72f, KeyframeAnimations.degreeVec(0.2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone311",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.04f, KeyframeAnimations.posVec(4.08f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.04f, KeyframeAnimations.posVec(4.08f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.92f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.96f, KeyframeAnimations.posVec(4.08f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone311",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.04f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.04f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.92f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.96f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone312",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.96f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.96f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.96f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone313",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.8f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.56f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.32f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.32f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone314",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone315",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.degreeVec(0f, 720f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone45",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone316",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.84f, KeyframeAnimations.degreeVec(0f, 51.375f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.degreeVec(0f, 232f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.08f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.08f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone317",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.36f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.92f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.28f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.72f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone318",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.84f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone319",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone320",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone321",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.92f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.12f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone323",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.88f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone324",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.6f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone326",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.96f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone327",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.12f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.4f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.12f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.8f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.56f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone329",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.04f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.16f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.36f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.16f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone330",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.44f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.48f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.88f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.64f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.56f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.56f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone332",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.6f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.24f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.64f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.96f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone333",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.12f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.4f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.12f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.8f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.56f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone335",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.04f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.16f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.36f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.16f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone336",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.6f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone338",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.6f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.24f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.64f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.96f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone339",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.44f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.16f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.84f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM))).build();
	public static final AnimationDefinition FLIGHT = AnimationDefinition.Builder.withLength(10f).looping()
			.addAnimation("bone19",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.posVec(-0.2f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.posVec(2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(2f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.28f, KeyframeAnimations.posVec(1.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(2.6f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone19",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone34",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.68f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.8f, KeyframeAnimations.posVec(-3f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.44f, KeyframeAnimations.posVec(-3f, 8f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone34",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.24f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.84f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.92f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone118",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.posVec(-0.2f, 8.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.posVec(-1f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.44f, KeyframeAnimations.posVec(-1f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(-1.5f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone118",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2.5f, 2.5f, 2.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.4f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.44f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone119",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.04f, KeyframeAnimations.posVec(-1.35f, 4.39f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.6f, KeyframeAnimations.posVec(0.81f, 6.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone119",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.84f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.64f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone127",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.28f, KeyframeAnimations.posVec(0.8f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.posVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.68f, KeyframeAnimations.posVec(-2f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(-1.5f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone127",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1.08f, KeyframeAnimations.scaleVec(2.5f, 2.5f, 2.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.28f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.68f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone128",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.32f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.32f, KeyframeAnimations.posVec(-1.35f, 4.39f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.24f, KeyframeAnimations.posVec(0.81f, 9.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.2f, KeyframeAnimations.posVec(2.86f, 2.2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.72f, KeyframeAnimations.posVec(4f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.2f, KeyframeAnimations.posVec(3.81f, 6.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.posVec(2.8f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone128",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.44f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone130",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.posVec(-0.2f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.posVec(2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(2f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.28f, KeyframeAnimations.posVec(1.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(2.6f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone130",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone131",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.68f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.8f, KeyframeAnimations.posVec(-3f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.44f, KeyframeAnimations.posVec(-3f, 8f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone131",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.24f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.84f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.92f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone133",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.posVec(-0.2f, 8.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.posVec(-1f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.44f, KeyframeAnimations.posVec(-1f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(-1.5f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone133",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(2.5f, 2.5f, 2.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.4f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.44f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone134",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.04f, KeyframeAnimations.posVec(-1.35f, 4.39f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.6f, KeyframeAnimations.posVec(0.81f, 6.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone134",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.84f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.64f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone136",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0.2f, 0.83f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.28f, KeyframeAnimations.posVec(0.8f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.posVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.68f, KeyframeAnimations.posVec(-2f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0.6f, 2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(-1.5f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone136",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1.08f, KeyframeAnimations.scaleVec(2.5f, 2.5f, 2.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.28f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.68f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone137",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.32f, KeyframeAnimations.posVec(0f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.32f, KeyframeAnimations.posVec(-1.35f, 4.39f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.24f, KeyframeAnimations.posVec(0.81f, 9.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.posVec(-0.2f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.2f, KeyframeAnimations.posVec(2.86f, 2.2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.72f, KeyframeAnimations.posVec(4f, -1.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.2f, KeyframeAnimations.posVec(3.81f, 6.62f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.posVec(2.8f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone137",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.44f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.52f, KeyframeAnimations.scaleVec(1.65f, 1.65f, 1.65f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.72f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone233",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(5.99989f, 0.02617f, -2.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-6.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.degreeVec(6.69497f, -0.00606f, 2.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.52f, KeyframeAnimations.degreeVec(-5.95f, 0f, -1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.4f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone233",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.12f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.6f, KeyframeAnimations.scaleVec(1f, 1.07f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.44f, KeyframeAnimations.scaleVec(1f, 0.97f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone234",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.degreeVec(3.84997f, 0.01308f, 1.52534f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.24f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.48f, KeyframeAnimations.degreeVec(4.69497f, -0.00606f, 0.99996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone234",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.32f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.8f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone236",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(5.99989f, 0.02617f, -2.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-6.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.degreeVec(6.69497f, -0.00606f, 2.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.52f, KeyframeAnimations.degreeVec(-5.95f, 0f, -1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.4f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone236",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.12f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.6f, KeyframeAnimations.scaleVec(1f, 1.07f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.44f, KeyframeAnimations.scaleVec(1f, 0.97f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone237",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.degreeVec(3.84997f, 0.01308f, 1.52534f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.24f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.48f, KeyframeAnimations.degreeVec(4.69497f, -0.00606f, 0.99996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone237",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.32f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.8f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone238",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(5.99989f, 0.02617f, -2.49931f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(-6.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.degreeVec(6.69497f, -0.00606f, 2.49996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.52f, KeyframeAnimations.degreeVec(-5.95f, 0f, -1.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.4f, KeyframeAnimations.degreeVec(-0.32f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone238",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.12f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.6f, KeyframeAnimations.scaleVec(1f, 1.07f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.44f, KeyframeAnimations.scaleVec(1f, 0.97f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone239",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.degreeVec(3.84997f, 0.01308f, 1.52534f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.24f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.48f, KeyframeAnimations.degreeVec(4.69497f, -0.00606f, 0.99996f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(-0.91f, -0.01f, 0.5f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone239",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.32f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.8f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone284",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone285",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone288",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone290",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone293",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone295",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone298",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone300",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone303",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone305",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone308",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.98f, 0.98f, 0.98f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone310",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.03f, 1.03f, 1.03f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(0.99f, 0.99f, 0.99f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1.08f, 1.08f, 1.08f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone59",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.44f, KeyframeAnimations.degreeVec(0f, 281.535f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 1440f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone62",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.8f, KeyframeAnimations.degreeVec(0f, 211.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.degreeVec(0f, 66.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.44f, KeyframeAnimations.degreeVec(0f, 158f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.degreeVec(0f, 72.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.04f, KeyframeAnimations.degreeVec(0f, -10.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.64f, KeyframeAnimations.degreeVec(0f, 10.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("increment",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.56f, KeyframeAnimations.degreeVec(0f, 294.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.48f, KeyframeAnimations.degreeVec(0f, 174f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.8f, KeyframeAnimations.degreeVec(0f, 219f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.88f, KeyframeAnimations.degreeVec(0f, -85f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone68",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.degreeVec(0f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7f, KeyframeAnimations.degreeVec(0f, -1f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone83",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(-4.534f, -1.04656f, -2.46736f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.degreeVec(-1.94f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.96f, KeyframeAnimations.degreeVec(-2.34f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.96f, KeyframeAnimations.degreeVec(1.53083f, 0.76068f, 2.57111f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.12f, KeyframeAnimations.degreeVec(-2.01784f, -0.94619f, -2.51383f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.8f, KeyframeAnimations.degreeVec(1.25f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.72f, KeyframeAnimations.degreeVec(0.2f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.96f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone311",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.52f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(4.875f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone311",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1.15f, 1.15f, 1.15f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone312",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.48f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.56f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.6f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.08f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.2f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.64f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1.04f, 1.04f, 1.04f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone313",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, -0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone314",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone315",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.96f, KeyframeAnimations.degreeVec(0f, -112.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.68f, KeyframeAnimations.degreeVec(0f, -65.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.56f, KeyframeAnimations.degreeVec(0f, -99.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.76f, KeyframeAnimations.degreeVec(0f, -2.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.16f, KeyframeAnimations.degreeVec(0f, -86.42f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.68f, KeyframeAnimations.degreeVec(0f, -72.67f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.12f, KeyframeAnimations.degreeVec(0f, -114.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.degreeVec(0f, -79.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.32f, KeyframeAnimations.degreeVec(0f, -106.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.08f, KeyframeAnimations.degreeVec(0f, -59.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.92f, KeyframeAnimations.degreeVec(0f, -86.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.48f, KeyframeAnimations.degreeVec(0f, -31.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.12f, KeyframeAnimations.degreeVec(0f, -59.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone45",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, -0.075f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone316",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -410.34f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.44f, KeyframeAnimations.degreeVec(0f, -119.29f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.84f, KeyframeAnimations.degreeVec(0f, 51.375f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.degreeVec(0f, 232f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.08f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.08f, KeyframeAnimations.degreeVec(0f, 1440f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.8f, KeyframeAnimations.degreeVec(0f, 1557f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.6f, KeyframeAnimations.degreeVec(0f, 1293f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 1440f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone317",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.4f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.36f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.92f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.92f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.28f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.72f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.64f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.84f, KeyframeAnimations.posVec(0f, 0.03f, -0.03f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone318",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone319",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone320",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone321",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.64f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.92f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.96f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.12f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rotor_bottom_T_add20",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.44f, KeyframeAnimations.posVec(0f, -5.4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, -10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.posVec(0f, 1.755f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.12f, KeyframeAnimations.posVec(0f, 3.325f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.2f, KeyframeAnimations.posVec(0f, 3.015f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 1.76f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.84f, KeyframeAnimations.posVec(0f, 0.24f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.6f, KeyframeAnimations.posVec(0f, -2.07f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.44f, KeyframeAnimations.posVec(0f, -6.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.44f, KeyframeAnimations.posVec(0f, -10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.84f, KeyframeAnimations.posVec(0f, 1.755f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.12f, KeyframeAnimations.posVec(0f, 3.325f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 3.015f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.6f, KeyframeAnimations.posVec(0f, 1.76f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.84f, KeyframeAnimations.posVec(0f, 0.24f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, -0.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rotor_bottom_T_add20",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.scaleVec(1.07f, 1.07f, 1.07f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.44f, KeyframeAnimations.scaleVec(1.07f, 1.07f, 1.07f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rotor_top_t_minus20",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 4.4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 9f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.posVec(0f, -1.755f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.12f, KeyframeAnimations.posVec(0f, -3.325f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.2f, KeyframeAnimations.posVec(0f, -3.015f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, -2.16f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.48f, KeyframeAnimations.posVec(0f, 4.4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.52f, KeyframeAnimations.posVec(0f, 8f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.84f, KeyframeAnimations.posVec(0f, -1.755f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.12f, KeyframeAnimations.posVec(0f, -3.325f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, -3.015f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.56f, KeyframeAnimations.posVec(0f, -2.16f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0.57f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rotor_top_t_minus20",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.44f, KeyframeAnimations.scaleVec(1.07f, 1.07f, 1.07f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.44f, KeyframeAnimations.scaleVec(1.07f, 1.07f, 1.07f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone323",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.88f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.2f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone324",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.6f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone326",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.96f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone327",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.44f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.16f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.84f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone329",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.08f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.2f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.72f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.2f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone330",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.6f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone332",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.96f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone333",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.44f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.16f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.84f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone335",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.08f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.2f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.72f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.4f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.2f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone336",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.92f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.6f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone338",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.scaleVec(1f, 1.05f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.64f, KeyframeAnimations.scaleVec(1f, 0.95f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.96f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0.93f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone339",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.28f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.scaleVec(1f, 1.03f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.44f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.16f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.16f, KeyframeAnimations.scaleVec(1f, 1.08f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.92f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.84f, KeyframeAnimations.scaleVec(1f, 0.85f, 1f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("monitor_pitch",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.28f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.8f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.36f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.28f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.8f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.16f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.24f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.76f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.12f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.8f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.44f, KeyframeAnimations.degreeVec(1.2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.72f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone112",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(-1.8f, 0f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(-2f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.degreeVec(0f, 0f, 0.6f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.8f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.degreeVec(-2f, 0f, 0.4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.36f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.64f, KeyframeAnimations.degreeVec(0f, 0f, -0.3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.24f, KeyframeAnimations.degreeVec(-1.8f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.degreeVec(0f, 0f, 0.4f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.16f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.degreeVec(0f, 0f, -0.2f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.2f, KeyframeAnimations.degreeVec(-1.8f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.56f, KeyframeAnimations.degreeVec(0f, 0f, 0.3f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.32f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.44f, KeyframeAnimations.degreeVec(-2f, 0f, 0.2f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.36f, KeyframeAnimations.degreeVec(-1.8f, 0f, -0.2f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.72f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone340",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0.75f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0.75f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0.75f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone342",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.degreeVec(1080f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();



	private final ModelPart throttle;
	private final ModelPart base_console;
	private final ModelPart anim_parts;
	private final ModelPart root;

	public CoralConsoleModel(ModelPart root) {
		this.root = root;
		this.base_console = root.getChild("base_console");
		this.throttle = base_console.getChild("controls").getChild("borders").getChild("bone23").getChild("bone17").getChild("throttle");
		this.anim_parts = root.getChild("anim_parts");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base_console = partdefinition.addOrReplaceChild("base_console", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rotor_bottom_T_add20 = base_console.addOrReplaceChild("rotor_bottom_T_add20", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -2.5F, -3.5F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -44.975F, -3.4437F));

		PartDefinition bone97 = rotor_bottom_T_add20.addOrReplaceChild("bone97", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.975F, 3.4437F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone98 = bone97.addOrReplaceChild("bone98", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -13.475F, -6.9437F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone99 = bone98.addOrReplaceChild("bone99", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone100 = bone99.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -13.475F, -6.9437F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone101 = bone100.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(77, 80).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition rotor_top_t_minus20 = base_console.addOrReplaceChild("rotor_top_t_minus20", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, -2.5F, -3.5F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -56.025F, -3.4187F));

		PartDefinition bone37 = rotor_top_t_minus20.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, 8.5F, -6.9187F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.975F, 3.4187F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, 8.475F, -6.9187F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, 8.5F, -6.9187F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, 8.475F, -6.9187F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, 8.5F, -6.9187F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition controls = base_console.addOrReplaceChild("controls", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 1.5F));

		PartDefinition north = controls.addOrReplaceChild("north", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.5F));

		PartDefinition bone35 = north.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(115, 9).addBox(-2.5F, -1.175F, 4.75F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(114, 98).addBox(2.25F, -0.275F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(9, 2).addBox(-2.0F, -0.525F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 2).addBox(-0.5F, -0.525F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 2).addBox(1.0F, -0.525F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone11 = bone35.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(100, 77).addBox(-4.5F, -0.5F, -2.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(111, 29).addBox(-2.5F, 0.1F, 1.75F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 16).addBox(-2.5F, -0.5F, 3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(84, 77).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-3.0F, -0.25F, 2.25F, 0.0F, 0.1745F, 0.0F));

		PartDefinition bone313 = bone11.addOrReplaceChild("bone313", CubeListBuilder.create().texOffs(50, 18).addBox(-0.5F, -0.525F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 3.75F));

		PartDefinition bone340 = bone11.addOrReplaceChild("bone340", CubeListBuilder.create().texOffs(124, 112).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -0.75F, -0.25F));

		PartDefinition bone341 = bone11.addOrReplaceChild("bone341", CubeListBuilder.create().texOffs(124, 112).addBox(-2.0322F, -2.0F, -27.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0322F, 0.75F, 27.5F));

		PartDefinition bone311 = bone11.addOrReplaceChild("bone311", CubeListBuilder.create().texOffs(84, 74).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.275F)), PartPose.offset(-2.075F, -0.5F, 0.5F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(74, 30).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition bone = bone35.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(117, 33).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(61, 116).addBox(-1.5F, -1.25F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.75F, -1.0F, 2.5F, -0.4363F, 0.0F, 0.0F));

		PartDefinition facing = bone.addOrReplaceChild("facing", CubeListBuilder.create().texOffs(14, 84).addBox(-0.5F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -0.85F, 0.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition bone15 = bone35.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(72, 107).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -0.525F, 6.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone14 = bone35.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(56, 104).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.525F, 6.5F, -0.8727F, 0.0F, 0.0F));

		PartDefinition bone10 = bone35.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(45, 119).addBox(-1.5F, -0.775F, 2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-2.0F, -2.375F, 6.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 94).addBox(-3.75F, -0.2623F, -0.0827F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone320 = bone10.addOrReplaceChild("bone320", CubeListBuilder.create().texOffs(23, 124).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -1.025F, 3.5F));

		PartDefinition bone318 = bone10.addOrReplaceChild("bone318", CubeListBuilder.create().texOffs(18, 94).addBox(-0.5F, -0.45F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, 0.2127F, 1.9173F));

		PartDefinition north_left = controls.addOrReplaceChild("north_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone53 = north_left.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(74, 16).addBox(-6.5F, -0.775F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 16).addBox(-5.0F, -0.775F, 1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 62).addBox(0.25F, -0.15F, 0.0F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(78, 66).addBox(-3.0F, -1.275F, 3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(116, 85).addBox(-1.0F, -0.65F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(124, 84).addBox(-1.0F, 0.35F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(36, 8).addBox(-0.5F, -0.525F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(40, 121).addBox(-0.5F, -0.275F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -0.525F, 6.75F, 0.0F, 0.3491F, 0.0F));

		PartDefinition bone61 = bone53.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(118, 103).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -0.025F, 3.75F));

		PartDefinition bone59 = bone61.addOrReplaceChild("bone59", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone56 = bone59.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(0, 72).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 0.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone57 = bone59.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.75F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(0, 72).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone312 = bone53.addOrReplaceChild("bone312", CubeListBuilder.create().texOffs(91, 66).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.275F)), PartPose.offset(-2.0F, -0.275F, 5.0F));

		PartDefinition bone55 = bone53.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(74, 16).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 92).addBox(-5.25F, 0.125F, -2.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(9, 2).addBox(1.75F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.275F, 1.75F, 0.0F, 0.3491F, 0.0F));

		PartDefinition bone60 = bone53.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(78, 92).addBox(-4.5F, -0.175F, 1.75F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone62 = bone60.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(100, 121).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -0.775F, 0.75F, 0.0F, 0.3927F, 0.0F));

		PartDefinition north_right = controls.addOrReplaceChild("north_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone28 = north_right.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(15, 123).addBox(5.5F, -1.025F, 1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 19).addBox(2.5F, -0.525F, 2.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 19).addBox(0.5F, -0.525F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(92, 63).addBox(-3.5F, -0.525F, 0.75F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 19).addBox(-1.5F, -0.525F, 2.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 19).addBox(-3.5F, -0.525F, 2.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(81, 0).addBox(-4.75F, -0.15F, 2.25F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 120).addBox(-2.75F, -0.375F, 6.75F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(108, 122).addBox(5.5F, -0.8F, 1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(36, 10).addBox(6.0F, -1.55F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone30 = bone28.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(102, 116).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(100, 85).addBox(-1.5F, -0.5F, -2.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(106, 0).addBox(-0.5F, 0.75F, -4.75F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -1.025F, 4.25F, 0.0F, 0.3054F, 0.0F));

		PartDefinition bone316 = bone30.addOrReplaceChild("bone316", CubeListBuilder.create().texOffs(91, 112).addBox(-1.25F, 0.0F, -0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, -1.1F, 0.25F));

		PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(61, 122).addBox(0.0F, 0.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition bone32 = bone30.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(7, 122).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(124, 0).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(124, 0).addBox(-5.5F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -0.525F, 1.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone317 = bone28.addOrReplaceChild("bone317", CubeListBuilder.create().texOffs(100, 66).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.275F));

		PartDefinition bone33 = bone28.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -2.375F, 6.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition south = controls.addOrReplaceChild("south", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone49 = south.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(30, 100).addBox(-1.5F, -0.525F, 4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(30, 100).addBox(2.5F, -0.525F, 3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(30, 100).addBox(-5.5F, -0.525F, 3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(119, 107).addBox(-7.5F, -0.275F, 0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 8).addBox(-7.75F, -0.15F, -0.25F, 15.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone319 = bone49.addOrReplaceChild("bone319", CubeListBuilder.create().texOffs(79, 19).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, 0.375F, 4.25F));

		PartDefinition bone69 = bone49.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(74, 43).addBox(-5.25F, -0.15F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone70 = bone69.addOrReplaceChild("bone70", CubeListBuilder.create().texOffs(117, 38).addBox(-1.5F, -0.85F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(122, 42).addBox(-1.0F, -0.95F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(95, 21).addBox(-2.0F, -0.85F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.325F, 3.5F, 0.0F, 0.3927F, 0.0F));

		PartDefinition increment = bone70.addOrReplaceChild("increment", CubeListBuilder.create().texOffs(62, 69).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.05F, 0.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone71 = bone70.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(95, 21).addBox(-2.0F, -0.35F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition south_left = controls.addOrReplaceChild("south_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone65 = south_left.addOrReplaceChild("bone65", CubeListBuilder.create().texOffs(117, 25).addBox(-4.75F, -0.275F, 1.75F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 100).addBox(-8.0F, -0.15F, 2.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone321 = bone65.addOrReplaceChild("bone321", CubeListBuilder.create().texOffs(125, 22).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 0.25F, 2.75F));

		PartDefinition bone72 = bone65.addOrReplaceChild("bone72", CubeListBuilder.create().texOffs(111, 5).addBox(-3.5F, -1.25F, -1.5F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(113, 53).addBox(-3.75F, -0.625F, 0.25F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, 0.475F, 3.25F, 0.0F, -0.1745F, 0.0F));

		PartDefinition bone66 = bone65.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(36, 8).addBox(0.75F, -0.15F, 0.25F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone67 = bone66.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(112, 111).addBox(-1.5F, -1.85F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -2.6F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.4F, 2.0F, 0.0F, 0.3054F, 0.0F));

		PartDefinition bone68 = bone67.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(32, 44).addBox(-1.0F, -0.8572F, -2.266F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6F, -2.3F, -0.6981F, 0.7418F, -0.829F));

		PartDefinition bone74 = bone66.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(33, 120).addBox(0.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -0.1F, 2.5F, 0.0F, 0.0F, -0.48F));

		PartDefinition south_right = controls.addOrReplaceChild("south_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone75 = south_right.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(42, 99).addBox(-4.75F, -0.775F, 2.75F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(74, 49).addBox(-3.75F, -0.275F, 0.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone314 = bone75.addOrReplaceChild("bone314", CubeListBuilder.create().texOffs(36, 114).addBox(-2.5F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(75, 27).addBox(-3.5F, 0.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -0.25F, 4.75F));

		PartDefinition bone80 = bone75.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(62, 56).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, -0.675F, 6.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone81 = bone75.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.35F, -0.675F, 4.25F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone79 = bone75.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(78, 98).addBox(-7.0F, -0.575F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.425F, 2.25F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone76 = bone75.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(50, 28).addBox(-1.5F, -0.775F, 2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 11).addBox(-1.0F, -1.275F, 3.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 11).addBox(-1.0F, -1.275F, 2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone77 = bone76.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(71, 121).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -0.525F, 1.5F, 0.0F, -0.3927F, 0.0F));

		PartDefinition bone315 = bone77.addOrReplaceChild("bone315", CubeListBuilder.create().texOffs(62, 69).addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -1.1F, 0.0F));

		PartDefinition bone78 = bone76.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(95, 14).addBox(-5.0F, -0.5F, -2.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.525F, 0.5F, 0.0F, -0.2182F, 0.0F));

		PartDefinition monitor_rotate = controls.addOrReplaceChild("monitor_rotate", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 1.0472F, 0.0F));

		PartDefinition monitor_pitch = monitor_rotate.addOrReplaceChild("monitor_pitch", CubeListBuilder.create().texOffs(18, 84).addBox(-5.0F, -7.7452F, -5.4376F, 10.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(109, 47).addBox(-3.5F, -6.7452F, -3.4376F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(18, 107).addBox(-4.0F, -6.9952F, -5.5126F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(100, 82).addBox(-5.0F, -0.7452F, -6.9376F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.75F, -9.6758F, 0.1309F, 0.0F, 0.0F));

		PartDefinition bone109 = monitor_pitch.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(0, 32).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.7452F, -1.4376F, 0.48F, 0.0F, 0.0F));

		PartDefinition bone110 = monitor_pitch.addOrReplaceChild("bone110", CubeListBuilder.create().texOffs(62, 87).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7452F, -3.4376F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone111 = monitor_pitch.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(0, 7).addBox(1.5F, -1.5F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).addBox(-3.5F, -0.5F, -1.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.7452F, -2.4376F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone112 = monitor_pitch.addOrReplaceChild("bone112", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, 0.25F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, -7.2452F, -5.4376F, -0.5672F, 0.0F, -0.2182F));

		PartDefinition bone113 = monitor_pitch.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, 0.25F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, -1.4952F, -5.4376F, -0.9163F, 0.0F, 0.0F));

		PartDefinition bone114 = monitor_pitch.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, 0.25F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-3.0F, -0.4952F, -6.9376F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone115 = monitor_pitch.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, 0.25F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.75F, -0.5952F, -6.9376F, -0.4363F, 0.0F, -0.2618F));

		PartDefinition borders = controls.addOrReplaceChild("borders", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone9 = borders.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone21 = bone9.addOrReplaceChild("bone21", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone22 = bone21.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(120, 65).addBox(-2.0F, -0.5F, 0.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.3801F, -30.3858F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone24 = bone9.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(46, 123).addBox(-1.0F, -32.5F, -12.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(48, 114).addBox(-2.0F, -32.25F, -5.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(62, 107).addBox(-2.0F, -31.95F, -0.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 66).addBox(1.5F, -32.95F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(124, 13).addBox(-2.25F, -32.25F, -8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(54, 120).addBox(0.25F, -32.0F, -8.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(8, 11).addBox(0.0F, -32.1F, -7.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone27 = bone24.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(76, 99).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -32.25F, -11.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone26 = bone24.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(34, 70).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -31.95F, 1.25F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(111, 118).addBox(-3.25F, -0.5F, -3.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.0F, -31.75F, -3.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone48 = borders.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone51 = bone48.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(0, 114).addBox(-2.0F, -33.75F, -6.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(121, 121).addBox(-1.5F, -34.25F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 114).addBox(-2.0F, -32.75F, -6.275F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(102, 21).addBox(-2.0F, -31.9F, -11.275F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(123, 90).addBox(-1.0F, -32.65F, -11.275F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition handbrake = bone51.addOrReplaceChild("handbrake", CubeListBuilder.create().texOffs(36, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -31.75F, -3.5F, -0.8727F, 0.0F, 0.0F));

		PartDefinition bone50 = borders.addOrReplaceChild("bone50", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -2.618F, 0.0F));

		PartDefinition bone52 = bone50.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(120, 57).addBox(-2.0F, -33.25F, -2.275F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 66).addBox(1.5F, -32.95F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 107).addBox(-2.0F, -31.95F, -0.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(61, 99).addBox(-2.0F, -31.95F, -10.25F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(32, 49).addBox(0.0F, -32.7F, -5.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone342 = bone52.addOrReplaceChild("bone342", CubeListBuilder.create().texOffs(97, 116).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -32.75F, -1.275F));

		PartDefinition bone73 = bone52.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(34, 70).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -31.95F, 1.25F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone63 = borders.addOrReplaceChild("bone63", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 2.618F, 0.0F));

		PartDefinition bone64 = bone63.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(101, 85).addBox(-2.0F, -31.9F, -7.775F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(78, 66).addBox(0.25F, -32.8F, -8.775F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(111, 33).addBox(-1.0F, -32.15F, -11.525F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(8, 7).addBox(2.25F, -29.9F, -14.775F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(101, 93).addBox(-1.0F, -32.65F, -11.525F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(27, 114).addBox(-0.5F, -32.8F, -0.275F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone83 = bone64.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(0, 62).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(23, 120).addBox(-1.0F, 7.0F, -1.25F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -28.15F, -14.775F, -0.3443F, -0.3931F, 0.1946F));

		PartDefinition bone23 = borders.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone13 = bone23.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(0, 75).addBox(-1.0F, -13.25F, -30.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone16 = bone13.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(120, 118).addBox(-1.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, -12.25F, -30.9F, 0.0F, 0.0F, -0.9599F));

		PartDefinition bone17 = bone23.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(50, 107).addBox(-1.5F, -32.5F, -10.5F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(117, 20).addBox(-1.75F, -32.75F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(39, 5).addBox(1.25F, -32.75F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(120, 61).addBox(0.0F, -32.0F, -2.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition throttle = bone17.addOrReplaceChild("throttle", CubeListBuilder.create().texOffs(11, 114).addBox(-0.25F, -1.0F, -0.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(51, 8).addBox(-0.75F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(73, 116).addBox(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -33.25F, -7.5F));

		PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(0, 121).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(81, 120).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -32.75F, -7.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone43 = borders.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(91, 122).addBox(-1.5F, -13.75F, -31.25F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(122, 76).addBox(-1.5F, -13.75F, -32.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone45 = bone44.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(85, 124).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.25F, -30.725F));

		PartDefinition bone46 = bone43.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(0, 106).addBox(-1.5F, -33.0F, -9.5F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(97, 98).addBox(-2.75F, -32.0F, -6.5F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(90, 115).addBox(-1.25F, -34.0F, -9.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(90, 115).addBox(0.25F, -34.0F, -9.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).addBox(-0.5F, -33.25F, -9.5F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone82 = bone46.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -32.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

		PartDefinition landtype = bone46.addOrReplaceChild("landtype", CubeListBuilder.create().texOffs(50, 16).addBox(-1.0F, -0.25F, -3.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(50, 22).addBox(-0.5F, -0.25F, -3.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, -32.5F, -10.0F));

		PartDefinition rotor = base_console.addOrReplaceChild("rotor", CubeListBuilder.create().texOffs(42, 53).addBox(-1.0F, -72.5F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone120 = rotor.addOrReplaceChild("bone120", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -81.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition bone121 = bone120.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone122 = bone121.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone123 = bone122.addOrReplaceChild("bone123", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone124 = bone123.addOrReplaceChild("bone124", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone125 = bone124.addOrReplaceChild("bone125", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone36 = rotor.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.5F, 0.0F));

		PartDefinition bone42 = bone36.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone47 = bone42.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone96 = bone47.addOrReplaceChild("bone96", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone108 = bone96.addOrReplaceChild("bone108", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone116 = bone108.addOrReplaceChild("bone116", CubeListBuilder.create().texOffs(99, 111).addBox(-4.0F, -13.5F, -6.9437F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone102 = rotor.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -19.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone103 = bone102.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone104 = bone103.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone105 = bone104.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone106 = bone105.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone107 = bone106.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, -53.5F, -6.0937F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition baseconsole = base_console.addOrReplaceChild("baseconsole", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone143 = baseconsole.addOrReplaceChild("bone143", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition bone144 = bone143.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone145 = bone144.addOrReplaceChild("bone145", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone146 = bone145.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone147 = bone146.addOrReplaceChild("bone147", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone148 = bone147.addOrReplaceChild("bone148", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone149 = bone148.addOrReplaceChild("bone149", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone150 = bone149.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone151 = bone150.addOrReplaceChild("bone151", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone152 = bone151.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone153 = bone152.addOrReplaceChild("bone153", CubeListBuilder.create().texOffs(87, 6).addBox(-6.25F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone154 = bone153.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(85, 49).addBox(-3.75F, 1.0F, -23.25F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone155 = baseconsole.addOrReplaceChild("bone155", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone156 = bone155.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone157 = bone155.addOrReplaceChild("bone157", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone158 = bone157.addOrReplaceChild("bone158", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone159 = bone157.addOrReplaceChild("bone159", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone160 = bone159.addOrReplaceChild("bone160", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone161 = bone159.addOrReplaceChild("bone161", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone162 = bone161.addOrReplaceChild("bone162", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone163 = bone161.addOrReplaceChild("bone163", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone164 = bone163.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone165 = bone163.addOrReplaceChild("bone165", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone166 = bone165.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(95, 30).addBox(-2.5F, -1.1206F, -2.316F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -21.5F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bone167 = baseconsole.addOrReplaceChild("bone167", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone168 = bone167.addOrReplaceChild("bone168", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone169 = bone167.addOrReplaceChild("bone169", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone170 = bone169.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone171 = bone169.addOrReplaceChild("bone171", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone172 = bone171.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone173 = bone171.addOrReplaceChild("bone173", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone174 = bone173.addOrReplaceChild("bone174", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone175 = bone173.addOrReplaceChild("bone175", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone176 = bone175.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone177 = bone175.addOrReplaceChild("bone177", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone178 = bone177.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(62, 66).addBox(-2.5F, 1.3512F, -1.4746F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.793F, -25.3451F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone179 = baseconsole.addOrReplaceChild("bone179", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone180 = bone179.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone181 = bone179.addOrReplaceChild("bone181", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone182 = bone181.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 1.8478F, -0.7654F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone183 = bone181.addOrReplaceChild("bone183", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone184 = bone183.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 1.8478F, -0.7654F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone185 = bone183.addOrReplaceChild("bone185", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone186 = bone185.addOrReplaceChild("bone186", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 1.8478F, -0.7654F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone187 = bone185.addOrReplaceChild("bone187", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone188 = bone187.addOrReplaceChild("bone188", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 1.8478F, -0.7654F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone189 = bone187.addOrReplaceChild("bone189", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone190 = bone189.addOrReplaceChild("bone190", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.8451F, -23.293F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone191 = baseconsole.addOrReplaceChild("bone191", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 1.5F));

		PartDefinition bone192 = bone191.addOrReplaceChild("bone192", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -22.9258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone193 = bone192.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone194 = bone191.addOrReplaceChild("bone194", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone195 = bone194.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone196 = bone195.addOrReplaceChild("bone196", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone197 = bone194.addOrReplaceChild("bone197", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone198 = bone197.addOrReplaceChild("bone198", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone199 = bone198.addOrReplaceChild("bone199", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone200 = bone197.addOrReplaceChild("bone200", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone201 = bone200.addOrReplaceChild("bone201", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone202 = bone201.addOrReplaceChild("bone202", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone203 = bone200.addOrReplaceChild("bone203", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone204 = bone203.addOrReplaceChild("bone204", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone205 = bone204.addOrReplaceChild("bone205", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone206 = bone203.addOrReplaceChild("bone206", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone207 = bone206.addOrReplaceChild("bone207", CubeListBuilder.create().texOffs(36, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone208 = bone207.addOrReplaceChild("bone208", CubeListBuilder.create().texOffs(32, 44).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone209 = baseconsole.addOrReplaceChild("bone209", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone210 = bone209.addOrReplaceChild("bone210", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone211 = bone210.addOrReplaceChild("bone211", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone212 = bone211.addOrReplaceChild("bone212", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone213 = bone212.addOrReplaceChild("bone213", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone214 = bone213.addOrReplaceChild("bone214", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, -14.5F, 5.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone215 = baseconsole.addOrReplaceChild("bone215", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition bone216 = bone215.addOrReplaceChild("bone216", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone217 = bone216.addOrReplaceChild("bone217", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone218 = bone217.addOrReplaceChild("bone218", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone219 = bone218.addOrReplaceChild("bone219", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone220 = bone219.addOrReplaceChild("bone220", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -13.5F, 11.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone221 = baseconsole.addOrReplaceChild("bone221", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition bone222 = bone221.addOrReplaceChild("bone222", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone223 = bone221.addOrReplaceChild("bone223", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone224 = bone223.addOrReplaceChild("bone224", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone225 = bone223.addOrReplaceChild("bone225", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone226 = bone225.addOrReplaceChild("bone226", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone227 = bone225.addOrReplaceChild("bone227", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone228 = bone227.addOrReplaceChild("bone228", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone229 = bone227.addOrReplaceChild("bone229", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone230 = bone229.addOrReplaceChild("bone230", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone231 = bone229.addOrReplaceChild("bone231", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone232 = bone231.addOrReplaceChild("bone232", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -1.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -21.4258F, -0.6981F, 0.0F, 0.0F));

		PartDefinition bone241 = baseconsole.addOrReplaceChild("bone241", CubeListBuilder.create(), PartPose.offset(0.0F, -11.5F, 0.0F));

		PartDefinition bone242 = bone241.addOrReplaceChild("bone242", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone243 = bone241.addOrReplaceChild("bone243", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone244 = bone243.addOrReplaceChild("bone244", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone245 = bone243.addOrReplaceChild("bone245", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone246 = bone245.addOrReplaceChild("bone246", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone247 = bone245.addOrReplaceChild("bone247", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone248 = bone247.addOrReplaceChild("bone248", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone249 = bone247.addOrReplaceChild("bone249", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone250 = bone249.addOrReplaceChild("bone250", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone251 = bone249.addOrReplaceChild("bone251", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone252 = bone251.addOrReplaceChild("bone252", CubeListBuilder.create().texOffs(99, 106).addBox(-3.5F, 0.0F, 0.75F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.3508F, -10.4187F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone253 = baseconsole.addOrReplaceChild("bone253", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone254 = bone253.addOrReplaceChild("bone254", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone255 = bone254.addOrReplaceChild("bone255", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone256 = bone255.addOrReplaceChild("bone256", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone257 = bone256.addOrReplaceChild("bone257", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone258 = bone257.addOrReplaceChild("bone258", CubeListBuilder.create().texOffs(64, 110).addBox(-2.5F, -8.75F, -10.2437F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone259 = baseconsole.addOrReplaceChild("bone259", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.5F, 0.0F));

		PartDefinition bone260 = bone259.addOrReplaceChild("bone260", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone261 = bone260.addOrReplaceChild("bone261", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone262 = bone261.addOrReplaceChild("bone262", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone263 = bone262.addOrReplaceChild("bone263", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone264 = bone263.addOrReplaceChild("bone264", CubeListBuilder.create().texOffs(62, 53).addBox(-4.0F, -12.5008F, -6.7937F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 71).addBox(-4.0F, -10.5008F, -6.9687F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone265 = baseconsole.addOrReplaceChild("bone265", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.5F, 0.0F));

		PartDefinition bone266 = bone265.addOrReplaceChild("bone266", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone267 = bone266.addOrReplaceChild("bone267", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone268 = bone267.addOrReplaceChild("bone268", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone269 = bone268.addOrReplaceChild("bone269", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone270 = bone269.addOrReplaceChild("bone270", CubeListBuilder.create().texOffs(106, 93).addBox(-4.0F, -11.475F, -8.9437F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone90 = baseconsole.addOrReplaceChild("bone90", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone91 = bone90.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone92 = bone91.addOrReplaceChild("bone92", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone93 = bone92.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone94 = bone93.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone95 = bone94.addOrReplaceChild("bone95", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -11.5F, -9.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone2 = baseconsole.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.5F, 0.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(101, 42).addBox(-4.5F, -11.475F, -9.7937F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone84 = baseconsole.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone86 = bone85.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone87 = bone86.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone88 = bone87.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(0, 49).addBox(-1.0F, -11.5F, -10.7437F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone271 = baseconsole.addOrReplaceChild("bone271", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -63.5F, 0.0F));

		PartDefinition bone272 = bone271.addOrReplaceChild("bone272", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone273 = bone272.addOrReplaceChild("bone273", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone274 = bone273.addOrReplaceChild("bone274", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone275 = bone274.addOrReplaceChild("bone275", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone276 = bone275.addOrReplaceChild("bone276", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -16.5F, -9.5437F, 11.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone277 = baseconsole.addOrReplaceChild("bone277", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -63.5F, 0.0F));

		PartDefinition bone278 = bone277.addOrReplaceChild("bone278", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone279 = bone278.addOrReplaceChild("bone279", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone280 = bone279.addOrReplaceChild("bone280", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone281 = bone280.addOrReplaceChild("bone281", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone282 = bone281.addOrReplaceChild("bone282", CubeListBuilder.create().texOffs(36, 104).addBox(-4.5F, -16.5F, -8.0437F, 9.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition anim_parts = partdefinition.addOrReplaceChild("anim_parts", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rotorparticles = anim_parts.addOrReplaceChild("rotorparticles", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone8 = rotorparticles.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, -17.5F, 0.25F));

		PartDefinition bone19 = bone8.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.975F, -6.9437F));

		PartDefinition bone34 = bone8.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition bone117 = rotorparticles.addOrReplaceChild("bone117", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone118 = bone117.addOrReplaceChild("bone118", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition bone119 = bone117.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.5F, -11.475F, -6.4437F));

		PartDefinition bone126 = rotorparticles.addOrReplaceChild("bone126", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone127 = bone126.addOrReplaceChild("bone127", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition bone128 = bone126.addOrReplaceChild("bone128", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.5F, -11.475F, -6.4437F));

		PartDefinition bone129 = rotorparticles.addOrReplaceChild("bone129", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.5F, 0.25F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone130 = bone129.addOrReplaceChild("bone130", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.975F, -6.4437F));

		PartDefinition bone131 = bone129.addOrReplaceChild("bone131", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition bone132 = rotorparticles.addOrReplaceChild("bone132", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.5F, 0.25F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone133 = bone132.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.975F, -6.4437F));

		PartDefinition bone134 = bone132.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition bone135 = rotorparticles.addOrReplaceChild("bone135", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -17.5F, 0.25F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone136 = bone135.addOrReplaceChild("bone136", CubeListBuilder.create().texOffs(83, 107).addBox(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(83, 107).addBox(0.5F, 2.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.975F, -6.9437F));

		PartDefinition bone137 = bone135.addOrReplaceChild("bone137", CubeListBuilder.create().texOffs(83, 107).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, -10.975F, -6.6937F));

		PartDefinition under_wires = anim_parts.addOrReplaceChild("under_wires", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone235 = under_wires.addOrReplaceChild("bone235", CubeListBuilder.create(), PartPose.offset(0.0F, -8.75F, -17.0508F));

		PartDefinition bone233 = bone235.addOrReplaceChild("bone233", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition bone140 = under_wires.addOrReplaceChild("bone140", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone238 = bone140.addOrReplaceChild("bone238", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.25F, -17.0508F));

		PartDefinition bone141 = under_wires.addOrReplaceChild("bone141", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone239 = bone141.addOrReplaceChild("bone239", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.25F, -17.0508F));

		PartDefinition bone139 = under_wires.addOrReplaceChild("bone139", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone237 = bone139.addOrReplaceChild("bone237", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.25F, -17.0508F));

		PartDefinition bone142 = under_wires.addOrReplaceChild("bone142", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone234 = bone142.addOrReplaceChild("bone234", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.25F, -17.0508F));

		PartDefinition bone138 = under_wires.addOrReplaceChild("bone138", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone236 = bone138.addOrReplaceChild("bone236", CubeListBuilder.create().texOffs(49, 35).addBox(-11.0F, -2.75F, -2.125F, 22.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(92, 57).addBox(-6.5F, 2.75F, 1.125F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.25F, -17.0508F));

		PartDefinition panels = anim_parts.addOrReplaceChild("panels", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 1.5F));

		PartDefinition bone20 = panels.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.5F));

		PartDefinition bone240 = bone20.addOrReplaceChild("bone240", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone284 = bone240.addOrReplaceChild("bone284", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone283 = bone240.addOrReplaceChild("bone283", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone285 = bone283.addOrReplaceChild("bone285", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone286 = panels.addOrReplaceChild("bone286", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone287 = bone286.addOrReplaceChild("bone287", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone288 = bone287.addOrReplaceChild("bone288", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone289 = bone287.addOrReplaceChild("bone289", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone290 = bone289.addOrReplaceChild("bone290", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone291 = panels.addOrReplaceChild("bone291", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone292 = bone291.addOrReplaceChild("bone292", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone293 = bone292.addOrReplaceChild("bone293", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone294 = bone292.addOrReplaceChild("bone294", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone295 = bone294.addOrReplaceChild("bone295", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone296 = panels.addOrReplaceChild("bone296", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone297 = bone296.addOrReplaceChild("bone297", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone298 = bone297.addOrReplaceChild("bone298", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone299 = bone297.addOrReplaceChild("bone299", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone300 = bone299.addOrReplaceChild("bone300", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone301 = panels.addOrReplaceChild("bone301", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone302 = bone301.addOrReplaceChild("bone302", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone303 = bone302.addOrReplaceChild("bone303", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone304 = bone302.addOrReplaceChild("bone304", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone305 = bone304.addOrReplaceChild("bone305", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone306 = panels.addOrReplaceChild("bone306", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone307 = bone306.addOrReplaceChild("bone307", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -21.4258F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone308 = bone307.addOrReplaceChild("bone308", CubeListBuilder.create().texOffs(133, 0).addBox(-9.5F, -0.25F, -3.5F, 19.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 3.5F));

		PartDefinition bone309 = bone307.addOrReplaceChild("bone309", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone310 = bone309.addOrReplaceChild("bone310", CubeListBuilder.create().texOffs(134, 9).addBox(-8.5F, -0.25F, -4.25F, 17.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.2F, 4.25F));

		PartDefinition bone322 = anim_parts.addOrReplaceChild("bone322", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone323 = bone322.addOrReplaceChild("bone323", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone324 = bone322.addOrReplaceChild("bone324", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		PartDefinition bone325 = anim_parts.addOrReplaceChild("bone325", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone326 = bone325.addOrReplaceChild("bone326", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone327 = bone325.addOrReplaceChild("bone327", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		PartDefinition bone328 = anim_parts.addOrReplaceChild("bone328", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition bone329 = bone328.addOrReplaceChild("bone329", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone330 = bone328.addOrReplaceChild("bone330", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		PartDefinition bone331 = anim_parts.addOrReplaceChild("bone331", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, 2.618F, 0.0F));

		PartDefinition bone332 = bone331.addOrReplaceChild("bone332", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone333 = bone331.addOrReplaceChild("bone333", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		PartDefinition bone334 = anim_parts.addOrReplaceChild("bone334", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone335 = bone334.addOrReplaceChild("bone335", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone336 = bone334.addOrReplaceChild("bone336", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		PartDefinition bone337 = anim_parts.addOrReplaceChild("bone337", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.3508F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone338 = bone337.addOrReplaceChild("bone338", CubeListBuilder.create().texOffs(134, 106).addBox(-1.0F, -20.5F, 0.475F, 2.0F, 20.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.6492F, -6.6937F));

		PartDefinition bone339 = bone337.addOrReplaceChild("bone339", CubeListBuilder.create().texOffs(142, 101).addBox(-1.0F, -21.0F, 0.5F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.1492F, -6.6937F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		base_console.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		anim_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root().getAllParts().forEach(ModelPart::resetPose);

		TardisClientData reactions = TardisClientData.getInstance(level.dimension());

		if (reactions.isFlying()) {
			this.animate(reactions.ROTOR_ANIMATION, FLIGHT, Minecraft.getInstance().player.tickCount);
		} else {
			if (TRConfig.CLIENT.PLAY_CONSOLE_IDLE_ANIMATIONS.get() && globalConsoleBlock != null) {
				this.animate(globalConsoleBlock.liveliness, IDLE, Minecraft.getInstance().player.tickCount);
			}
		}


		float rot = 0f + ( 2f * ((float)reactions.getThrottleStage() / TardisPilotingManager.MAX_THROTTLE_STAGE));
		this.throttle.xRot = rot;

		base_console.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		anim_parts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ResourceLocation getDefaultTexture() {
		return CORAL_TEXTURE;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}
}