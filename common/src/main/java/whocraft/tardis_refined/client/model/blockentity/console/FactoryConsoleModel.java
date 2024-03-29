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

public class FactoryConsoleModel extends HierarchicalModel implements ConsoleUnit {

	private final ModelPart root;
	private final ModelPart throttleLever;
	private final ModelPart handbrake;

	private static final ResourceLocation FACTORY_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/factory/factory_console.png");


	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(10f).looping()
			.addAnimation("dialspin",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 32.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.56f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.84f, KeyframeAnimations.degreeVec(0f, 70f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.36f, KeyframeAnimations.degreeVec(0f, 55f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.44f, KeyframeAnimations.degreeVec(0f, 92.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7f, KeyframeAnimations.degreeVec(0f, 52.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.12f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.56f, KeyframeAnimations.degreeVec(0f, 22.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.52f, KeyframeAnimations.degreeVec(0f, -10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.84f, KeyframeAnimations.degreeVec(0f, -2.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.88f, KeyframeAnimations.degreeVec(0f, -25f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.08f, KeyframeAnimations.degreeVec(0f, 10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.56f, KeyframeAnimations.degreeVec(0f, -20f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.32f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.24f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.76f, KeyframeAnimations.degreeVec(0f, 10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin3",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.24f, KeyframeAnimations.degreeVec(0f, 11.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.88f, KeyframeAnimations.degreeVec(0f, 10.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.48f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.08f, KeyframeAnimations.degreeVec(0f, -9f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.degreeVec(0f, 10.96f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.12f, KeyframeAnimations.degreeVec(0f, 11.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.64f, KeyframeAnimations.degreeVec(0f, 8f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.88f, KeyframeAnimations.degreeVec(0f, -6f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin4",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.72f, KeyframeAnimations.degreeVec(0f, 13.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.4f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.36f, KeyframeAnimations.degreeVec(0f, -14f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.36f, KeyframeAnimations.degreeVec(0f, 10.96f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.56f, KeyframeAnimations.degreeVec(0f, -21.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.16f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.32f, KeyframeAnimations.degreeVec(0f, -14.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone168",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone170",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone172",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone173",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone177",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone178",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone181",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.32f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone182",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone186",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.32f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone188",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.84f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone192",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone194",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone205",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(4.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.92f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone217",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.36f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.88f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.92f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone218",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(1.12f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.16f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.44f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.48f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.8f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.44f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.8f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.84f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone219",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.08f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.12f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.44f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.48f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.24f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.56f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone220",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.92f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone221",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone222",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.12f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone223",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone224",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone225",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone226",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.36f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition FLIGHT = AnimationDefinition.Builder.withLength(10f).looping()
			.addAnimation("bone62",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("dialspin",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -27f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -50.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.48f, KeyframeAnimations.degreeVec(0f, -26.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.4f, KeyframeAnimations.degreeVec(0f, -39f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.52f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.52f, KeyframeAnimations.degreeVec(0f, -36.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.64f, KeyframeAnimations.degreeVec(0f, -26.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.84f, KeyframeAnimations.degreeVec(0f, -32.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.04f, KeyframeAnimations.degreeVec(0f, -13.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.96f, KeyframeAnimations.degreeVec(0f, -33f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.6f, KeyframeAnimations.degreeVec(0f, -26f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, -27f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.76f, KeyframeAnimations.degreeVec(0f, -22f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.12f, KeyframeAnimations.degreeVec(0f, -14f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.56f, KeyframeAnimations.degreeVec(0f, -20f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.28f, KeyframeAnimations.degreeVec(0f, 46.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.degreeVec(0f, -21.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.36f, KeyframeAnimations.degreeVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin3",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.04f, KeyframeAnimations.degreeVec(0f, 16.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.16f, KeyframeAnimations.degreeVec(0f, -4.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.84f, KeyframeAnimations.degreeVec(0f, 10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.44f, KeyframeAnimations.degreeVec(0f, 4.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.degreeVec(0f, -33.25f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.92f, KeyframeAnimations.degreeVec(0f, 10.96f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.56f, KeyframeAnimations.degreeVec(0f, 69.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.68f, KeyframeAnimations.degreeVec(0f, -1.25f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.68f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.28f, KeyframeAnimations.degreeVec(0f, 9.25f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("dialspin4",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.68f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.64f, KeyframeAnimations.degreeVec(0f, -63.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.48f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3.4f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.44f, KeyframeAnimations.degreeVec(0f, -25f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5.36f, KeyframeAnimations.degreeVec(0f, 23.46f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.56f, KeyframeAnimations.degreeVec(0f, -28.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.16f, KeyframeAnimations.degreeVec(0f, -7f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.28f, KeyframeAnimations.degreeVec(0f, -14.5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.92f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone168",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone170",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone172",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone173",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone177",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.52f, KeyframeAnimations.posVec(2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.52f, KeyframeAnimations.posVec(2.5f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone178",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone181",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.32f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.64f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone182",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone186",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.32f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone188",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.84f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone192",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.24f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone194",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone205",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
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
							new Keyframe(5.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone217",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.44f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.28f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.32f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.56f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone218",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.96f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone219",
					new AnimationChannel(AnimationChannel.Targets.SCALE,
							new Keyframe(0.2f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.56f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.64f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.24f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.64f, KeyframeAnimations.scaleVec(1.05f, 1.05f, 1.05f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.68f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone220",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.92f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone221",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.8f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone222",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.12f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.68f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.72f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone223",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.44f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.64f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.68f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone224",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.76f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.12f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.16f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.48f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.52f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.84f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone225",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.88f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.92f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.28f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.2f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.24f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.08f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.72f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.76f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.48f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone226",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.52f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.56f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.4f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.56f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.36f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6.04f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rotorhead",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.85f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0.75f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.48f, KeyframeAnimations.posVec(0f, -4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4.56f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.08f, KeyframeAnimations.posVec(0f, 1.345f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5.64f, KeyframeAnimations.posVec(0f, 1f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.96f, KeyframeAnimations.posVec(0f, -4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(9.52f, KeyframeAnimations.posVec(0f, 0.75f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.posVec(0f, 0.85f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("rotorhead",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.degreeVec(0f, -10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("bone193",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone204",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
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
							new Keyframe(5.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone210",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(9f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone211",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(3.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.04f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(7.96f, KeyframeAnimations.posVec(0f, 0.05f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("bone227",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
									AnimationChannel.Interpolations.CATMULLROM))).build();

	private final ModelPart console_factory;

	public FactoryConsoleModel(ModelPart root) {
		this.console_factory = root.getChild("console_factory");
		this.root = root;
		this.throttleLever = findPart(this, "lever2");
		this.handbrake = (ModelPart) getAnyDescendantWithName("lever3").get();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition console_factory = partdefinition.addOrReplaceChild("console_factory", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone69 = console_factory.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(0, 13).addBox(-7.0F, -18.5F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition bone57 = bone69.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offset(0.0F, -15.5F, 0.0F));

		PartDefinition bone63 = bone57.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone64 = bone58.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone61 = bone59.addOrReplaceChild("bone61", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone62 = bone61.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone65 = bone61.addOrReplaceChild("bone65", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone66 = bone65.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone67 = bone65.addOrReplaceChild("bone67", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone68 = bone67.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone45 = bone69.addOrReplaceChild("bone45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -14.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone51 = bone45.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone47 = bone46.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone48 = bone46.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone49 = bone48.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone50 = bone48.addOrReplaceChild("bone50", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone52 = bone50.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone53 = bone50.addOrReplaceChild("bone53", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone55 = bone53.addOrReplaceChild("bone55", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(26, 41).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -19.9F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone14 = bone69.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

		PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone19 = bone18.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(46, 62).addBox(-0.5F, -14.0F, -9.875F, 1.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone70 = bone14.addOrReplaceChild("bone70", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone76 = bone70.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.5F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone71 = bone70.addOrReplaceChild("bone71", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone77 = bone71.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.0F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone72 = bone71.addOrReplaceChild("bone72", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone73 = bone72.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.0F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone74 = bone72.addOrReplaceChild("bone74", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone75 = bone74.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.0F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone78 = bone74.addOrReplaceChild("bone78", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone79 = bone78.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.0F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone80 = bone78.addOrReplaceChild("bone80", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(44, 46).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(0.0F, -14.0F, -19.9F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone21 = bone14.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone22 = bone21.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone26 = bone25.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(0, 41).addBox(-4.5F, -1.0F, -8.8F, 9.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone33 = bone14.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone39 = bone33.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone36 = bone34.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone38 = bone36.addOrReplaceChild("bone38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone40 = bone38.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone41 = bone38.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone43 = bone41.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(49, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.8F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone27 = bone14.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone32 = bone31.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(0, 53).addBox(-4.0F, -18.0F, -7.0F, 8.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone13 = bone69.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition bone7 = bone13.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(5, 29).addBox(-0.5F, -2.0F, -19.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone = bone13.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(42, 41).addBox(-9.5F, -2.0F, -17.475F, 19.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone94 = bone69.addOrReplaceChild("bone94", CubeListBuilder.create(), PartPose.offset(0.0F, -20.5F, 0.0F));

		PartDefinition bone95 = bone94.addOrReplaceChild("bone95", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone96 = bone95.addOrReplaceChild("bone96", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone97 = bone96.addOrReplaceChild("bone97", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone98 = bone97.addOrReplaceChild("bone98", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone99 = bone98.addOrReplaceChild("bone99", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone100 = bone99.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(25, 76).addBox(-0.5F, -1.994F, -8.118F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone119 = bone94.addOrReplaceChild("bone119", CubeListBuilder.create(), PartPose.offset(0.0F, 0.3F, 0.0F));

		PartDefinition bone125 = bone119.addOrReplaceChild("bone125", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone120 = bone119.addOrReplaceChild("bone120", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone126 = bone120.addOrReplaceChild("bone126", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone121 = bone120.addOrReplaceChild("bone121", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone122 = bone121.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone123 = bone121.addOrReplaceChild("bone123", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone124 = bone123.addOrReplaceChild("bone124", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone127 = bone123.addOrReplaceChild("bone127", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone128 = bone127.addOrReplaceChild("bone128", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone129 = bone127.addOrReplaceChild("bone129", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone130 = bone129.addOrReplaceChild("bone130", CubeListBuilder.create().texOffs(19, 57).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.494F, 7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone101 = bone94.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone102 = bone101.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone103 = bone102.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone104 = bone103.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone105 = bone104.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone106 = bone105.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(55, 68).addBox(-4.0F, -1.994F, -7.057F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone107 = bone94.addOrReplaceChild("bone107", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone113 = bone107.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone108 = bone107.addOrReplaceChild("bone108", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone109 = bone108.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone110 = bone108.addOrReplaceChild("bone110", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone112 = bone110.addOrReplaceChild("bone112", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone114 = bone112.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone115 = bone112.addOrReplaceChild("bone115", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone116 = bone115.addOrReplaceChild("bone116", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone117 = bone115.addOrReplaceChild("bone117", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone118 = bone117.addOrReplaceChild("bone118", CubeListBuilder.create().texOffs(62, 13).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.994F, -7.057F, -0.5672F, 0.0F, 0.0F));

		PartDefinition bone82 = bone69.addOrReplaceChild("bone82", CubeListBuilder.create(), PartPose.offset(0.0F, -14.5F, 0.0F));

		PartDefinition bone88 = bone82.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone83 = bone82.addOrReplaceChild("bone83", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone89 = bone83.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone84 = bone83.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone86 = bone84.addOrReplaceChild("bone86", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone87 = bone86.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone90 = bone86.addOrReplaceChild("bone90", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone91 = bone90.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone92 = bone90.addOrReplaceChild("bone92", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone93 = bone92.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(0, 29).addBox(-9.5F, -1.0F, 0.0F, 19.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -16.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition rotorhead = console_factory.addOrReplaceChild("rotorhead", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));

		PartDefinition bone227 = rotorhead.addOrReplaceChild("bone227", CubeListBuilder.create().texOffs(38, 65).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition balls = bone227.addOrReplaceChild("balls", CubeListBuilder.create().texOffs(70, 59).addBox(-3.5F, -1.0F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.1F, 0.0F));

		PartDefinition mirror = bone227.addOrReplaceChild("mirror", CubeListBuilder.create().texOffs(68, 17).addBox(-3.5F, -2.9F, -0.5F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone135 = bone227.addOrReplaceChild("bone135", CubeListBuilder.create(), PartPose.offset(0.0F, 26.75F, 0.0F));

		PartDefinition bone141 = bone135.addOrReplaceChild("bone141", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone136 = bone135.addOrReplaceChild("bone136", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone142 = bone136.addOrReplaceChild("bone142", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone137 = bone136.addOrReplaceChild("bone137", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone138 = bone137.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone139 = bone137.addOrReplaceChild("bone139", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone140 = bone139.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone143 = bone139.addOrReplaceChild("bone143", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone144 = bone143.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone145 = bone143.addOrReplaceChild("bone145", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone146 = bone145.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(30, 57).addBox(-2.0F, 0.0F, 0.675F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -23.0F, -4.325F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone147 = rotorhead.addOrReplaceChild("bone147", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition bone153 = bone147.addOrReplaceChild("bone153", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone148 = bone147.addOrReplaceChild("bone148", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone154 = bone148.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone149 = bone148.addOrReplaceChild("bone149", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone150 = bone149.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone151 = bone149.addOrReplaceChild("bone151", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone152 = bone151.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone155 = bone151.addOrReplaceChild("bone155", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone156 = bone155.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone157 = bone155.addOrReplaceChild("bone157", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone158 = bone157.addOrReplaceChild("bone158", CubeListBuilder.create().texOffs(43, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.2F, -0.0175F, 0.0F, 0.0F));

		PartDefinition bone160 = rotorhead.addOrReplaceChild("bone160", CubeListBuilder.create().texOffs(42, 48).addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.5F, 0.0F));

		PartDefinition bone161 = bone160.addOrReplaceChild("bone161", CubeListBuilder.create().texOffs(42, 48).mirror().addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone162 = bone161.addOrReplaceChild("bone162", CubeListBuilder.create().texOffs(42, 48).addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone163 = bone162.addOrReplaceChild("bone163", CubeListBuilder.create().texOffs(42, 48).mirror().addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone164 = bone163.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(42, 48).addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone165 = bone164.addOrReplaceChild("bone165", CubeListBuilder.create().texOffs(42, 48).mirror().addBox(-2.5F, -22.0F, -4.575F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone132 = rotorhead.addOrReplaceChild("bone132", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -27.0F, 4.25F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(0, 76).addBox(-1.0F, -20.0F, 4.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone217 = bone132.addOrReplaceChild("bone217", CubeListBuilder.create().texOffs(0, 79).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.225F)), PartPose.offset(0.0F, -23.5F, 4.75F));

		PartDefinition bone133 = bone132.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -27.0F, 4.25F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(0, 76).addBox(-1.0F, -20.0F, 4.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone218 = bone133.addOrReplaceChild("bone218", CubeListBuilder.create().texOffs(0, 79).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.225F)), PartPose.offset(0.0F, -23.5F, 4.75F));

		PartDefinition bone134 = bone133.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, -27.0F, 4.25F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(0, 76).addBox(-1.0F, -20.0F, 4.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone219 = bone134.addOrReplaceChild("bone219", CubeListBuilder.create().texOffs(0, 79).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.225F)), PartPose.offset(0.0F, -23.5F, 4.75F));

		PartDefinition controls = console_factory.addOrReplaceChild("controls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition north = controls.addOrReplaceChild("north", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition bone159 = north.addOrReplaceChild("bone159", CubeListBuilder.create().texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(70, 71).addBox(-2.5F, -0.75F, 4.25F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(42, 45).addBox(-2.25F, -0.775F, 4.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(29, 45).addBox(2.5F, -0.1F, 0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(29, 45).mirror().addBox(-4.5F, -0.1F, 0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone226 = bone159.addOrReplaceChild("bone226", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition bone170 = bone159.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(81, 3).addBox(-2.25F, -17.25F, -12.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition lever = bone159.addOrReplaceChild("lever", CubeListBuilder.create().texOffs(9, 13).addBox(-0.5F, -2.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 2.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition switchboard = bone159.addOrReplaceChild("switchboard", CubeListBuilder.create().texOffs(0, 23).addBox(-2.25F, 0.0F, 0.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 5.25F, 0.3054F, 0.0F, 0.0F));

		PartDefinition bone166 = bone159.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(33, 67).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.4F, 2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone172 = bone166.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(30, 76).addBox(-4.0F, -16.675F, -15.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 16.1F, 15.0F));

		PartDefinition bone167 = bone159.addOrReplaceChild("bone167", CubeListBuilder.create().texOffs(33, 67).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 0.4F, 2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone173 = bone167.addOrReplaceChild("bone173", CubeListBuilder.create().texOffs(30, 76).addBox(3.0F, -16.675F, -15.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 16.1F, 15.0F));

		PartDefinition bone131 = bone159.addOrReplaceChild("bone131", CubeListBuilder.create().texOffs(30, 57).addBox(6.0F, -2.75F, -9.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 57).addBox(4.75F, -2.75F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 57).addBox(3.5F, -2.75F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 57).mirror().addBox(-4.5F, -2.75F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 29).mirror().addBox(-5.0F, -2.1F, -4.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 29).mirror().addBox(-6.25F, -2.1F, -7.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 29).mirror().addBox(-7.5F, -2.1F, -10.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(72, 29).addBox(5.5F, -2.1F, -10.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 29).addBox(4.25F, -2.1F, -7.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 29).addBox(3.0F, -2.1F, -4.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 57).mirror().addBox(-5.75F, -2.75F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 57).mirror().addBox(-7.0F, -2.75F, -9.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 10.75F));

		PartDefinition bone193 = bone131.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 14.5F, 6.25F));

		PartDefinition bone194 = bone131.addOrReplaceChild("bone194", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.25F, 14.5F, 9.25F));

		PartDefinition bone204 = bone131.addOrReplaceChild("bone204", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 14.5F, 12.25F));

		PartDefinition bone205 = bone131.addOrReplaceChild("bone205", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.5F, 14.5F, 12.25F));

		PartDefinition bone210 = bone131.addOrReplaceChild("bone210", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(11.75F, 14.5F, 9.25F));

		PartDefinition bone211 = bone131.addOrReplaceChild("bone211", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-7.0F, -17.225F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(13.0F, 14.5F, 6.25F));

		PartDefinition north_right = controls.addOrReplaceChild("north_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone169 = north_right.addOrReplaceChild("bone169", CubeListBuilder.create().texOffs(61, 9).addBox(0.75F, -0.025F, 0.25F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(71, 5).addBox(-2.5F, -0.25F, 4.25F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(57, 24).addBox(-4.0F, -0.1F, 4.55F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -0.75F, 0.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-4.5F, -0.5F, 1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-7.0F, -0.75F, 0.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone220 = bone169.addOrReplaceChild("bone220", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition bone221 = bone169.addOrReplaceChild("bone221", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.725F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 13.5F));

		PartDefinition bone192 = bone169.addOrReplaceChild("bone192", CubeListBuilder.create().texOffs(77, 23).addBox(-4.0F, -16.575F, -11.45F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition lever2 = bone169.addOrReplaceChild("lever2", CubeListBuilder.create().texOffs(68, 75).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 2.25F, 0.8727F, 0.0F, 0.0F));

		PartDefinition lever3 = bone169.addOrReplaceChild("lever3", CubeListBuilder.create().texOffs(68, 75).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -0.75F, 2.25F, 0.8727F, 0.0F, 0.0F));

		PartDefinition switchboard2 = bone169.addOrReplaceChild("switchboard2", CubeListBuilder.create().texOffs(71, 49).addBox(-3.25F, 0.0F, 0.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -0.75F, 3.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition bone171 = bone169.addOrReplaceChild("bone171", CubeListBuilder.create().texOffs(69, 55).addBox(-3.5F, -2.5F, -6.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(43, 24).addBox(-3.25F, -2.525F, -6.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 1.75F, 8.25F));

		PartDefinition bone168 = bone171.addOrReplaceChild("bone168", CubeListBuilder.create().texOffs(78, 0).addBox(1.25F, -15.25F, -14.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 12.75F, 8.75F));

		PartDefinition south_right = controls.addOrReplaceChild("south_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone174 = south_right.addOrReplaceChild("bone174", CubeListBuilder.create().texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(20, 76).addBox(-0.5F, -0.25F, 6.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 66).addBox(-4.5F, -0.5F, 4.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(28, 72).addBox(-5.0F, -0.75F, 1.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(74, 66).mirror().addBox(3.5F, -0.5F, 4.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(19, 67).addBox(-2.5F, 0.15F, 3.75F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(17, 72).addBox(4.25F, -0.05F, 3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone222 = bone174.addOrReplaceChild("bone222", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition smallswitch = bone174.addOrReplaceChild("smallswitch", CubeListBuilder.create().texOffs(9, 73).addBox(-0.5F, 0.0F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, -0.5F, 5.5F, 0.6545F, 0.0F, 0.0F));

		PartDefinition smallswitch2 = bone174.addOrReplaceChild("smallswitch2", CubeListBuilder.create().texOffs(9, 73).mirror().addBox(-0.5F, 0.0F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.25F, -0.5F, 5.5F, 0.6545F, 0.0F, 0.0F));

		PartDefinition mediumswitch = bone174.addOrReplaceChild("mediumswitch", CubeListBuilder.create().texOffs(15, 76).addBox(-0.5F, 0.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -0.9F, 3.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition mediumswitch2 = bone174.addOrReplaceChild("mediumswitch2", CubeListBuilder.create().texOffs(15, 76).addBox(-0.5F, 0.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -0.9F, 1.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition bone175 = bone174.addOrReplaceChild("bone175", CubeListBuilder.create().texOffs(0, 19).addBox(-5.0F, -2.25F, -7.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(73, 63).addBox(-3.9F, -2.55F, -6.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(0, 73).addBox(-3.9F, -2.55F, -7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(4.0F, 1.75F, 8.25F));

		PartDefinition bone188 = bone175.addOrReplaceChild("bone188", CubeListBuilder.create().texOffs(81, 66).addBox(0.1F, -17.275F, -15.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-4.0F, 14.75F, 8.75F));

		PartDefinition bone184 = bone174.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(28, 72).addBox(-2.5F, -2.5F, -3.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 1.75F, 4.0F));

		PartDefinition bone189 = bone174.addOrReplaceChild("bone189", CubeListBuilder.create().texOffs(29, 45).addBox(2.5F, -2.1F, -9.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.0F, 10.5F));

		PartDefinition bone176 = bone189.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(33, 67).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -1.6F, -8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone186 = bone176.addOrReplaceChild("bone186", CubeListBuilder.create().texOffs(30, 76).addBox(3.0F, -16.675F, -15.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 16.1F, 15.0F));

		PartDefinition bone179 = bone174.addOrReplaceChild("bone179", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.25F, 6.75F, 0.0F, 2.2689F, 0.0F));

		PartDefinition bone180 = bone179.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(75, 75).addBox(0.0F, -0.5F, -0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition south = controls.addOrReplaceChild("south", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone183 = south.addOrReplaceChild("bone183", CubeListBuilder.create().texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 72).addBox(4.25F, -0.05F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(68, 35).addBox(0.25F, -0.05F, 0.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(19, 61).addBox(-3.0F, -0.55F, 4.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-6.25F, -0.05F, 0.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(30, 57).mirror().addBox(-5.0F, -0.25F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 57).mirror().addBox(-5.0F, -0.25F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 57).mirror().addBox(-5.0F, -0.25F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(19, 61).addBox(-3.0F, -0.55F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(19, 61).addBox(-3.0F, -0.55F, 0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(19, 61).addBox(-1.5F, -0.55F, 0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(19, 53).addBox(-1.25F, -0.65F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone223 = bone183.addOrReplaceChild("bone223", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition bone181 = bone183.addOrReplaceChild("bone181", CubeListBuilder.create().texOffs(30, 59).mirror().addBox(-5.0F, -16.725F, -14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 59).mirror().addBox(-5.0F, -16.725F, -16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(83, 6).addBox(-5.25F, -16.525F, -15.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition mediumswitch3 = bone183.addOrReplaceChild("mediumswitch3", CubeListBuilder.create().texOffs(15, 76).addBox(-0.5F, 0.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.9F, 6.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition mediumswitch4 = bone183.addOrReplaceChild("mediumswitch4", CubeListBuilder.create().texOffs(15, 76).addBox(-0.5F, 0.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.9F, 6.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition smallswitch3 = bone183.addOrReplaceChild("smallswitch3", CubeListBuilder.create().texOffs(19, 53).addBox(-0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -0.55F, 5.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition smallswitch4 = bone183.addOrReplaceChild("smallswitch4", CubeListBuilder.create().texOffs(19, 57).addBox(-0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -0.55F, 2.75F, 0.3927F, 0.0F, 0.0F));

		PartDefinition smallswitch5 = bone183.addOrReplaceChild("smallswitch5", CubeListBuilder.create().texOffs(19, 57).addBox(-0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -0.55F, 0.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone190 = bone183.addOrReplaceChild("bone190", CubeListBuilder.create().texOffs(29, 45).addBox(2.5F, -2.1F, -9.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 14.5F));

		PartDefinition bone191 = bone190.addOrReplaceChild("bone191", CubeListBuilder.create().texOffs(33, 67).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -1.6F, -8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone182 = bone191.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(30, 76).addBox(3.0F, -16.675F, -15.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 16.1F, 15.0F));

		PartDefinition bone185 = bone183.addOrReplaceChild("bone185", CubeListBuilder.create().texOffs(28, 72).addBox(-2.5F, -2.5F, -3.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.75F, 9.0F));

		PartDefinition bone187 = bone183.addOrReplaceChild("bone187", CubeListBuilder.create().texOffs(28, 72).addBox(-2.5F, -2.5F, -3.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 1.75F, 9.0F));

		PartDefinition south_left = controls.addOrReplaceChild("south_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone195 = south_left.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(49, 35).addBox(-4.5F, -0.05F, 0.25F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(55, 65).addBox(-3.75F, -0.05F, 2.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-5.75F, -0.05F, 3.25F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).mirror().addBox(3.75F, -0.05F, 3.25F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone224 = bone195.addOrReplaceChild("bone224", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -16.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 17.0F));

		PartDefinition bone178 = bone195.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(55, 79).addBox(-3.75F, -14.525F, -14.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 78).addBox(0.5F, -14.525F, -16.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.5F, 17.0F));

		PartDefinition dialspin = bone195.addOrReplaceChild("dialspin", CubeListBuilder.create().texOffs(50, 0).addBox(-0.75F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.75F, -0.6F, 4.5F, 0.0F, -0.3491F, 0.0F));

		PartDefinition dialspin2 = bone195.addOrReplaceChild("dialspin2", CubeListBuilder.create().texOffs(50, 0).addBox(-0.75F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, -0.6F, 4.5F, 0.0F, 0.829F, 0.0F));

		PartDefinition bone196 = bone195.addOrReplaceChild("bone196", CubeListBuilder.create().texOffs(43, 13).addBox(-0.75F, -0.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, 0.2F, 1.25F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone197 = bone195.addOrReplaceChild("bone197", CubeListBuilder.create().texOffs(5, 36).addBox(-0.75F, -0.35F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 0.2F, 1.25F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone198 = bone195.addOrReplaceChild("bone198", CubeListBuilder.create().texOffs(5, 36).addBox(-0.75F, -0.35F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, 0.2F, 1.25F, 0.0F, -2.3562F, 0.0F));

		PartDefinition bone199 = bone195.addOrReplaceChild("bone199", CubeListBuilder.create(), PartPose.offset(1.0F, 2.0F, 14.5F));

		PartDefinition bone200 = bone199.addOrReplaceChild("bone200", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -1.6F, -8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone201 = bone195.addOrReplaceChild("bone201", CubeListBuilder.create().texOffs(29, 41).addBox(-5.75F, -2.5F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 1.75F, 9.0F));

		PartDefinition bone202 = bone201.addOrReplaceChild("bone202", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -2.65F, -2.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition bone203 = bone195.addOrReplaceChild("bone203", CubeListBuilder.create().texOffs(29, 41).addBox(-0.75F, -2.5F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(0, 45).addBox(-3.25F, -2.25F, -5.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 45).addBox(-5.75F, -2.25F, -5.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 1.75F, 9.0F));

		PartDefinition bone206 = bone203.addOrReplaceChild("bone206", CubeListBuilder.create().texOffs(43, 16).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -2.25F, -4.5F, 0.5672F, 0.0F, 0.0F));

		PartDefinition bone207 = bone203.addOrReplaceChild("bone207", CubeListBuilder.create().texOffs(43, 16).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -2.25F, -4.5F, 0.5672F, 0.0F, 0.0F));

		PartDefinition north_left = controls.addOrReplaceChild("north_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone208 = north_left.addOrReplaceChild("bone208", CubeListBuilder.create().texOffs(71, 5).addBox(-2.5F, -0.5F, 7.75F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(58, 45).addBox(-0.75F, -0.05F, 1.25F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(58, 49).addBox(-5.25F, -0.2F, 2.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(17, 72).addBox(-4.5F, -0.05F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-7.75F, -0.5F, 0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(5, 33).addBox(5.75F, -0.3F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 51).addBox(2.5F, -0.15F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 62).addBox(-1.0F, -0.05F, 0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(0.25F, -0.05F, 6.75F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -17.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone225 = bone208.addOrReplaceChild("bone225", CubeListBuilder.create().texOffs(82, 11).addBox(-2.0F, -10.975F, -9.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.5F, 17.0F));

		PartDefinition dialspin3 = bone208.addOrReplaceChild("dialspin3", CubeListBuilder.create().texOffs(50, 0).addBox(-0.75F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.6F, 4.5F, 0.0F, -1.2654F, 0.0F));

		PartDefinition dialspin4 = bone208.addOrReplaceChild("dialspin4", CubeListBuilder.create().texOffs(50, 0).addBox(-0.75F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, -0.6F, 4.5F, 0.0F, 0.829F, 0.0F));

		PartDefinition bone209 = bone208.addOrReplaceChild("bone209", CubeListBuilder.create().texOffs(55, 71).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 4).addBox(-1.0F, -0.75F, -1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -0.2F, 6.75F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bone177 = bone209.addOrReplaceChild("bone177", CubeListBuilder.create().texOffs(55, 76).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.525F, -2.525F));

		PartDefinition bone212 = bone208.addOrReplaceChild("bone212", CubeListBuilder.create(), PartPose.offset(1.0F, 2.0F, 14.5F));

		PartDefinition bone213 = bone212.addOrReplaceChild("bone213", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -1.6F, -8.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone214 = bone208.addOrReplaceChild("bone214", CubeListBuilder.create().texOffs(29, 41).addBox(-0.75F, -2.5F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 1.75F, 9.0F));

		PartDefinition bone215 = bone214.addOrReplaceChild("bone215", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -2.65F, -2.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition bone216 = bone208.addOrReplaceChild("bone216", CubeListBuilder.create().texOffs(29, 41).addBox(-0.25F, -2.5F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(3.5F, 1.75F, 9.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root().getAllParts().forEach(ModelPart::resetPose);
		console_factory.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root().getAllParts().forEach(ModelPart::resetPose);
		TardisClientData reactions = TardisClientData.getInstance(level.dimension());

		if (globalConsoleBlock != null && globalConsoleBlock.getBlockState().getValue(GlobalConsoleBlock.POWERED)) {
			if (reactions.isFlying()) {
				this.animate(reactions.ROTOR_ANIMATION, FLIGHT, Minecraft.getInstance().player.tickCount);
			} else {
				if (TRConfig.CLIENT.PLAY_CONSOLE_IDLE_ANIMATIONS.get() && globalConsoleBlock != null) {
					this.animate(globalConsoleBlock.liveliness, IDLE, Minecraft.getInstance().player.tickCount);
				}
			}
		}

		float rot = -125 - ( 30 * ((float) reactions.getThrottleStage() / TardisPilotingManager.MAX_THROTTLE_STAGE));
		this.throttleLever.xRot = rot;

		this.handbrake.xRot = reactions.isHandbrakeEngaged() ? -155f : -125f;

		console_factory.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	@Override
	public ResourceLocation getDefaultTexture() {
		return FACTORY_TEXTURE;
	}
}