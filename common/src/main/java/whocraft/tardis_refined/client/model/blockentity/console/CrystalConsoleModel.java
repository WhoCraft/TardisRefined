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
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

public class CrystalConsoleModel extends HierarchicalModel implements IConsoleUnit {

    private static final ResourceLocation CRYSTAL_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/crystal/crystal_console.png");

    public static final AnimationDefinition MODEL_FLIGHT_LOOP = AnimationDefinition.Builder.withLength(2f).looping()
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -6f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();


    public static final AnimationDefinition CORE = AnimationDefinition.Builder.withLength(1.5f).looping()
            .addAnimation("core",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(360f, 360f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    private final ModelPart base_control;
    private final ModelPart rotor;
    private final ModelPart controls;
    private final ModelPart root;
    private final ModelPart throttle;
    private final CrystalCoreModel core;


    public CrystalConsoleModel(ModelPart root) {
        this.root = root;
        this.base_control = root.getChild("base_control");
        this.rotor = root.getChild("rotor");
        this.controls = root.getChild("controls");
        this.throttle = controls.getChild("north_left_side").getChild("large_lever_control_throttle");
        core = new CrystalCoreModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelRegistry.CRYSTAL_CORE));
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base_control = partdefinition.addOrReplaceChild("base_control", CubeListBuilder.create(), PartPose.offset(0.0F, 27.0F, 0.0F));

        PartDefinition root_r1 = base_control.addOrReplaceChild("root_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 17.0F, 16.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone = base_control.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(105, 85).addBox(-5.0F, -2.0F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(0, 81).addBox(-6.0F, -2.025F, -22.3F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone13 = base_control.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition bone14 = bone13.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(76, 31).addBox(-0.5F, -0.5F, -25.65F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(86, 35).addBox(-1.0F, -3.0F, -24.65F, 2.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone97 = base_control.addOrReplaceChild("bone97", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone98 = bone97.addOrReplaceChild("bone98", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone99 = bone97.addOrReplaceChild("bone99", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone100 = bone99.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone101 = bone99.addOrReplaceChild("bone101", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone102 = bone101.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone103 = bone101.addOrReplaceChild("bone103", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone104 = bone103.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone105 = bone103.addOrReplaceChild("bone105", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone106 = bone105.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone107 = bone105.addOrReplaceChild("bone107", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone108 = bone107.addOrReplaceChild("bone108", CubeListBuilder.create().texOffs(86, 54).addBox(-4.5F, 0.0F, -9.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone55 = base_control.addOrReplaceChild("bone55", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone57 = bone55.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone59 = bone57.addOrReplaceChild("bone59", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone61 = bone59.addOrReplaceChild("bone61", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone62 = bone61.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone63 = bone61.addOrReplaceChild("bone63", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone64 = bone63.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone65 = bone63.addOrReplaceChild("bone65", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone66 = bone65.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(23, 34).addBox(-9.5F, 0.0F, 0.0F, 19.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone19 = base_control.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition bone25 = bone19.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone22 = bone20.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone24 = bone22.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone26 = bone24.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone27 = bone24.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone29 = bone27.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(80, 75).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone49 = base_control.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -25.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone51 = bone50.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone52 = bone51.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(68, 75).addBox(-4.5F, -2.0F, -11.4F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone43 = base_control.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone45 = bone44.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone47 = bone46.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone48 = bone47.addOrReplaceChild("bone48", CubeListBuilder.create().texOffs(15, 126).addBox(-3.5F, 1.0F, -10.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone37 = base_control.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(121, 42).addBox(-2.0F, -3.0F, -12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone31 = base_control.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition bone31_r1 = bone31.addOrReplaceChild("bone31_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone32 = bone31.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone32_r1 = bone32.addOrReplaceChild("bone32_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone33_r1 = bone33.addOrReplaceChild("bone33_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone34_r1 = bone34.addOrReplaceChild("bone34_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone35_r1 = bone35.addOrReplaceChild("bone35_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone36_r1 = bone36.addOrReplaceChild("bone36_r1", CubeListBuilder.create().texOffs(126, 125).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone70 = base_control.addOrReplaceChild("bone70", CubeListBuilder.create(), PartPose.offset(0.0F, -26.0F, 0.0F));

        PartDefinition bone71 = bone70.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone87 = bone70.addOrReplaceChild("bone87", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone88 = bone87.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone89 = bone87.addOrReplaceChild("bone89", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone90 = bone89.addOrReplaceChild("bone90", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone91 = bone89.addOrReplaceChild("bone91", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone92 = bone91.addOrReplaceChild("bone92", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone93 = bone91.addOrReplaceChild("bone93", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone94 = bone93.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(101, 0).addBox(-0.5F, 0.25F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone95 = bone93.addOrReplaceChild("bone95", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone96 = bone95.addOrReplaceChild("bone96", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 31).addBox(-2.0F, 0.0F, 0.025F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, -0.9163F, 0.0F, 0.0F));

        PartDefinition bone68 = base_control.addOrReplaceChild("bone68", CubeListBuilder.create(), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition bone69 = bone68.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone77 = bone68.addOrReplaceChild("bone77", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone79 = bone77.addOrReplaceChild("bone79", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone80 = bone79.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone81 = bone79.addOrReplaceChild("bone81", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone82 = bone81.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone83 = bone81.addOrReplaceChild("bone83", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone84 = bone83.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F))
                .texOffs(0, 92).addBox(-0.5F, -7.0F, -1.4F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone85 = bone83.addOrReplaceChild("bone85", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone86 = bone85.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 17.5F, -12.75F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone109 = base_control.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition bone110 = bone109.addOrReplaceChild("bone110", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone112 = bone111.addOrReplaceChild("bone112", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone114 = bone113.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -3.0F, -11.75F, 5.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone121 = base_control.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(0, 105).addBox(-3.5F, -3.0F, -10.75F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 119).addBox(-5.5F, -1.0F, -16.75F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 0).addBox(-6.0F, -1.0F, -17.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(126, 0).mirror().addBox(-6.0F, 10.0F, -17.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone122 = bone121.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(0, 105).addBox(-3.5F, -3.0F, -10.75F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone123 = bone122.addOrReplaceChild("bone123", CubeListBuilder.create().texOffs(0, 105).addBox(-3.5F, -3.0F, -10.75F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(109, 125).addBox(-2.0F, 4.0F, -18.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(76, 31).addBox(-7.0F, -0.5F, -18.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 131).addBox(-5.0F, 4.5F, -18.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 131).mirror().addBox(3.0F, 4.5F, -18.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(128, 73).addBox(-7.5F, -1.5F, -18.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(128, 73).addBox(5.5F, -1.5F, -18.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(76, 31).mirror().addBox(6.0F, -0.5F, -18.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone123_r1 = bone123.addOrReplaceChild("bone123_r1", CubeListBuilder.create().texOffs(0, 132).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.5F, -18.25F, 0.0F, 0.0F, -0.48F));

        PartDefinition bone124 = bone123.addOrReplaceChild("bone124", CubeListBuilder.create().texOffs(0, 105).addBox(-3.5F, -3.0F, -10.75F, 7.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(105, 73).addBox(-4.0F, -1.0F, -15.75F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone125 = bone124.addOrReplaceChild("bone125", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone126 = bone125.addOrReplaceChild("bone126", CubeListBuilder.create().texOffs(23, 34).addBox(-2.5F, 6.0F, -14.75F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(108, 15).addBox(-4.5F, 2.0F, -15.25F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 118).addBox(2.5F, 2.5F, -14.75F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone126_r1 = bone126.addOrReplaceChild("bone126_r1", CubeListBuilder.create().texOffs(0, 132).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.5F, -15.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition bone132 = bone126.addOrReplaceChild("bone132", CubeListBuilder.create().texOffs(113, 54).addBox(-8.0F, -1.5F, 0.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 4.0F, -14.75F, 0.0F, 0.3927F, 0.0F));

        PartDefinition bone132_r1 = bone132.addOrReplaceChild("bone132_r1", CubeListBuilder.create().texOffs(17, 119).addBox(-7.0F, -1.525F, 0.0F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 1.5272F, 0.0F));

        PartDefinition bone127 = bone121.addOrReplaceChild("bone127", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone133 = bone127.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(26, 103).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -24.6F, -0.3491F, 0.0F, 0.0F));

        PartDefinition bone128 = bone127.addOrReplaceChild("bone128", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone129 = bone128.addOrReplaceChild("bone129", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone130 = bone129.addOrReplaceChild("bone130", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone131 = bone130.addOrReplaceChild("bone131", CubeListBuilder.create().texOffs(53, 103).addBox(-1.5F, -6.0F, -26.75F, 3.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone67 = base_control.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition bone72 = bone67.addOrReplaceChild("bone72", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone73 = bone72.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone75 = bone74.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone76 = bone75.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(106, 31).addBox(-2.0F, -3.0F, -12.75F, 4.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone115 = base_control.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone116 = bone115.addOrReplaceChild("bone116", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone117 = bone116.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone118 = bone117.addOrReplaceChild("bone118", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone119 = bone118.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone120 = bone119.addOrReplaceChild("bone120", CubeListBuilder.create().texOffs(49, 0).addBox(-9.5F, -1.05F, -20.15F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rotor = partdefinition.addOrReplaceChild("rotor", CubeListBuilder.create().texOffs(65, 15).addBox(-7.0F, -24.475F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(31, 75).addBox(-6.0F, -41.0F, -6.0F, 12.0F, 15.0F, 12.0F, new CubeDeformation(-0.25F))
                .texOffs(80, 95).addBox(-3.0F, -47.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(43, 50).addBox(-7.0F, -34.0F, -7.0F, 14.0F, 10.0F, 14.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition root_r2 = rotor.addOrReplaceChild("root_r2", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -50.0F, -10.0F, 1.0F, 26.0F, 20.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition root_r3 = rotor.addOrReplaceChild("root_r3", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -50.0F, -10.0F, 1.0F, 26.0F, 20.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition controls = partdefinition.addOrReplaceChild("controls", CubeListBuilder.create(), PartPose.offset(0.0F, 27.0F, 0.0F));

        PartDefinition north_right = controls.addOrReplaceChild("north_right", CubeListBuilder.create().texOffs(0, 87).addBox(-4.5F, -14.5F, -10.3F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone134 = north_right.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(128, 61).addBox(-1.5F, -2.0F, 8.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(6, 92).addBox(5.5F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 82).addBox(-4.5F, -1.5F, 5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition button_control = bone134.addOrReplaceChild("button_control", CubeListBuilder.create().texOffs(91, 75).addBox(-0.5F, -18.5F, -11.3F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 20.3F));

        PartDefinition bone136 = bone134.addOrReplaceChild("bone136", CubeListBuilder.create().texOffs(51, 127).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, -1.0F, 1.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition large_valve_control = bone136.addOrReplaceChild("large_valve_control", CubeListBuilder.create().texOffs(0, 132).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.7F, 0.0F, 0.0F, -0.6981F));

        PartDefinition lever_control = bone134.addOrReplaceChild("lever_control", CubeListBuilder.create().texOffs(80, 87).addBox(-2.5F, -0.5F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -0.525F, 1.5F, 0.0F, 1.1781F, 0.0F));

        PartDefinition bone146 = bone134.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone147 = bone134.addOrReplaceChild("bone147", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone139 = north_right.addOrReplaceChild("bone139", CubeListBuilder.create().texOffs(21, 87).addBox(0.5F, -1.375F, -5.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(21, 87).addBox(0.5F, -1.375F, -7.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(86, 118).addBox(-2.0F, -0.85F, -8.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(86, 118).addBox(-2.0F, -0.85F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone140 = bone139.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(101, 0).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, -5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition small_valve_control = bone139.addOrReplaceChild("small_valve_control", CubeListBuilder.create().texOffs(127, 15).addBox(-1.25F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.15F, -5.0F, 0.0F, -0.7418F, 0.0F));

        PartDefinition small_valve2_control = bone139.addOrReplaceChild("small_valve2_control", CubeListBuilder.create().texOffs(127, 15).addBox(-1.25F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.15F, -7.0F, 0.0F, -1.3526F, 0.0F));

        PartDefinition north_left = controls.addOrReplaceChild("north_left", CubeListBuilder.create().texOffs(114, 10).addBox(-5.5F, -14.5F, -10.3F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(121, 97).addBox(-2.75F, -11.5F, -15.3F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone135 = north_left.addOrReplaceChild("bone135", CubeListBuilder.create().texOffs(119, 108).addBox(-2.0F, -0.5F, 5.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(112, 118).addBox(-2.75F, -0.6F, 5.25F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(89, 44).addBox(0.75F, -2.1F, 6.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone150 = bone135.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(64, 127).addBox(-8.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition switch_control = bone150.addOrReplaceChild("switch_control", CubeListBuilder.create(), PartPose.offset(-7.0F, -1.0F, 0.75F));

        PartDefinition bone150_r1 = switch_control.addOrReplaceChild("bone150_r1", CubeListBuilder.create().texOffs(77, 128).addBox(0.0F, -0.75F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition bone151 = bone135.addOrReplaceChild("bone151", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone152 = north_left.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(108, 24).addBox(-1.5F, -0.975F, -8.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition control_random = bone152.addOrReplaceChild("control_random", CubeListBuilder.create().texOffs(132, 83).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.975F, -6.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone148 = bone152.addOrReplaceChild("bone148", CubeListBuilder.create().texOffs(101, 0).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, -5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition west = controls.addOrReplaceChild("west", CubeListBuilder.create().texOffs(0, 87).addBox(-4.5F, -14.5F, -10.3F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition exterior_control = west.addOrReplaceChild("exterior_control", CubeListBuilder.create().texOffs(133, 103).addBox(1.0F, -24.0F, -14.4F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(140, 111).addBox(1.0F, -24.75F, -14.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition bone159 = west.addOrReplaceChild("bone159", CubeListBuilder.create().texOffs(76, 44).addBox(-2.0F, -1.0F, 8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 129).addBox(-7.0F, -0.5F, 1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(21, 87).addBox(-6.5F, -1.875F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(21, 87).addBox(-6.5F, -1.875F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(68, 82).addBox(-4.5F, -1.5F, 5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone159_r1 = bone159.addOrReplaceChild("bone159_r1", CubeListBuilder.create().texOffs(42, 110).addBox(-0.5F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -1.0F, 8.75F, -0.3491F, 0.0F, 0.0F));

        PartDefinition small_valve3_control = bone159.addOrReplaceChild("small_valve3_control", CubeListBuilder.create().texOffs(127, 15).addBox(-1.25F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.65F, 3.5F, 0.0F, -0.7418F, 0.0F));

        PartDefinition small_valve4_control = bone159.addOrReplaceChild("small_valve4_control", CubeListBuilder.create().texOffs(127, 15).addBox(-1.25F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.65F, 1.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition bone162 = bone159.addOrReplaceChild("bone162", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone163 = bone159.addOrReplaceChild("bone163", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone164 = west.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(118, 26).addBox(-2.5F, -0.6F, -8.25F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(57, 0).addBox(2.75F, -0.85F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 0).mirror().addBox(-3.75F, -0.85F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(43, 60).addBox(-2.0F, -1.1F, -8.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone166 = bone164.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(121, 33).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6F, -8.25F, 0.48F, 0.0F, 0.0F));

        PartDefinition switch2_control = bone166.addOrReplaceChild("switch2_control", CubeListBuilder.create().texOffs(101, 10).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -1.0F));

        PartDefinition south_right = controls.addOrReplaceChild("south_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition bone168 = south_right.addOrReplaceChild("bone168", CubeListBuilder.create().texOffs(65, 23).addBox(-2.5F, -4.0F, 8.5F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone170 = bone168.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(64, 127).addBox(-8.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition switch3_control = bone170.addOrReplaceChild("switch3_control", CubeListBuilder.create(), PartPose.offset(-7.0F, -1.0F, 0.75F));

        PartDefinition bone170_r1 = switch3_control.addOrReplaceChild("bone170_r1", CubeListBuilder.create().texOffs(77, 128).addBox(0.0F, -0.75F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition bone172 = bone168.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone173 = south_right.addOrReplaceChild("bone173", CubeListBuilder.create().texOffs(86, 118).addBox(-3.25F, -0.85F, -7.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(128, 67).addBox(-1.0F, -1.1F, -7.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(86, 118).mirror().addBox(1.25F, -0.85F, -7.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
                .texOffs(86, 118).addBox(-1.0F, -0.85F, -5.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(23, 50).addBox(-2.0F, -0.85F, -3.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(121, 51).addBox(-1.0F, -1.85F, -2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition large_valve2_control_rotate = bone173.addOrReplaceChild("large_valve2_control_rotate", CubeListBuilder.create().texOffs(65, 103).addBox(-1.75F, -0.5F, -1.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.85F, -6.75F, 0.0F, -0.4363F, 0.0F));

        PartDefinition bone175 = bone173.addOrReplaceChild("bone175", CubeListBuilder.create().texOffs(101, 0).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, -5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition south_left = controls.addOrReplaceChild("south_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition bone191 = south_left.addOrReplaceChild("bone191", CubeListBuilder.create().texOffs(17, 105).addBox(-2.0F, -4.0F, 6.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 82).addBox(-0.5F, -6.0F, 8.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone193 = bone191.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(64, 127).addBox(-8.0F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition switch4_control_increment = bone193.addOrReplaceChild("switch4_control_increment", CubeListBuilder.create(), PartPose.offset(-7.0F, -1.0F, 0.75F));

        PartDefinition bone193_r1 = switch4_control_increment.addOrReplaceChild("bone193_r1", CubeListBuilder.create().texOffs(77, 128).addBox(0.0F, -0.75F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition bone194 = bone191.addOrReplaceChild("bone194", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone195 = south_left.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(128, 67).addBox(-1.0F, -1.1F, -6.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(91, 118).addBox(-3.0F, 0.5F, -11.75F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(29, 81).addBox(-2.0F, 0.4F, -10.75F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(21, 87).addBox(-2.75F, -0.875F, -10.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(21, 87).addBox(-0.5F, -0.875F, -10.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(21, 87).mirror().addBox(1.75F, -0.875F, -10.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition small_valve5_control_x = bone195.addOrReplaceChild("small_valve5_control_x", CubeListBuilder.create().texOffs(127, 15).addBox(-1.25F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -0.65F, -10.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition small_valve6_control_z = bone195.addOrReplaceChild("small_valve6_control_z", CubeListBuilder.create().texOffs(127, 15).mirror().addBox(-0.75F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, -0.65F, -10.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition small_valve7_control_y = bone195.addOrReplaceChild("small_valve7_control_y", CubeListBuilder.create().texOffs(127, 15).mirror().addBox(-0.75F, 0.0F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.65F, -10.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition lever2_control = bone195.addOrReplaceChild("lever2_control", CubeListBuilder.create().texOffs(65, 31).addBox(-0.5F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.1F, -5.75F, 0.0F, -0.8727F, 0.0F));

        PartDefinition bone197 = bone195.addOrReplaceChild("bone197", CubeListBuilder.create().texOffs(101, 0).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, -5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition east = controls.addOrReplaceChild("east", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone180 = east.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(65, 23).addBox(-2.5F, -4.0F, 8.5F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(128, 115).addBox(4.5F, -0.5F, 0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(108, 118).addBox(-3.5F, -1.0F, 5.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 103).addBox(-0.5F, -1.25F, 2.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(105, 111).addBox(-0.75F, -1.35F, 5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 118).mirror().addBox(1.5F, -1.0F, 5.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition large_lever4_control_door_toggle = bone180.addOrReplaceChild("large_lever4_control_door_toggle", CubeListBuilder.create().texOffs(65, 15).addBox(-0.25F, -1.5F, -4.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(65, 15).mirror().addBox(-0.75F, -1.5F, -4.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 105).addBox(-0.5F, -2.5F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -0.5F, 2.25F, -0.6981F, 0.0F, 0.0F));

        PartDefinition large_valve3_control = bone180.addOrReplaceChild("large_valve3_control", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.0F, -1.0F, -0.7F, -0.1745F, -1.1345F, 0.0F));

        PartDefinition bone189_r1 = large_valve3_control.addOrReplaceChild("bone189_r1", CubeListBuilder.create().texOffs(0, 132).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.25F, 0.0F, 0.0F, -0.6545F));

        PartDefinition switch5_control = bone180.addOrReplaceChild("switch5_control", CubeListBuilder.create().texOffs(73, 15).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -1.0F, 6.5F, -0.48F, 0.0F, 0.0F));

        PartDefinition switch6_control = bone180.addOrReplaceChild("switch6_control", CubeListBuilder.create().texOffs(73, 15).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, 6.5F, -0.48F, 0.0F, 0.0F));

        PartDefinition bone182 = bone180.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(100, 64).addBox(-11.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone183 = bone180.addOrReplaceChild("bone183", CubeListBuilder.create().texOffs(100, 64).mirror().addBox(1.0F, -0.5F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4F, 5.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone184 = east.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(86, 118).addBox(-3.25F, -0.85F, -5.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(0, 92).addBox(-3.75F, -0.1F, -12.25F, 7.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(86, 118).mirror().addBox(1.25F, -0.85F, -5.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
                .texOffs(91, 118).addBox(-3.0F, 0.5F, -11.75F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -12.3F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone185 = east.addOrReplaceChild("bone185", CubeListBuilder.create().texOffs(70, 108).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -14.5F, -9.3F, 0.0F, -0.48F, 0.0F));

        PartDefinition spinnything_control = bone185.addOrReplaceChild("spinnything_control", CubeListBuilder.create().texOffs(86, 54).addBox(-1.25F, -2.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.5F, -0.5F, -0.7418F, 0.0F, 0.0F));

        PartDefinition north_side = controls.addOrReplaceChild("north_side", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition bone144 = north_side.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(105, 95).addBox(-1.0F, -2.75F, -1.0F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone144_r1 = bone144.addOrReplaceChild("bone144_r1", CubeListBuilder.create().texOffs(43, 50).addBox(-2.0F, -1.5F, -4.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, 10.75F, -0.5672F, 0.0F, 0.0F));

        PartDefinition microphone_control = bone144.addOrReplaceChild("microphone_control", CubeListBuilder.create().texOffs(65, 15).addBox(-0.25F, -23.0F, -11.05F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 103).addBox(-1.25F, -24.5F, -11.05F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 20.3F));

        PartDefinition bone144_r2 = microphone_control.addOrReplaceChild("bone144_r2", CubeListBuilder.create().texOffs(124, 131).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -23.0F, -11.55F, -0.6545F, 0.0F, 0.0F));

        PartDefinition north_right_side = controls.addOrReplaceChild("north_right_side", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone137 = north_right_side.addOrReplaceChild("bone137", CubeListBuilder.create().texOffs(105, 95).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone137_r1 = bone137.addOrReplaceChild("bone137_r1", CubeListBuilder.create().texOffs(87, 126).addBox(0.0F, 0.0F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.5F, 5.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition binocular_control = bone137.addOrReplaceChild("binocular_control", CubeListBuilder.create().texOffs(105, 101).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 10.0F, 0.0F, -0.5672F, 0.0F));

        PartDefinition bone138 = binocular_control.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(23, 44).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bone138_r1 = bone138.addOrReplaceChild("bone138_r1", CubeListBuilder.create().texOffs(100, 64).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -0.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition south_left_side = controls.addOrReplaceChild("south_left_side", CubeListBuilder.create().texOffs(121, 91).addBox(-2.5F, -13.5F, -13.05F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(105, 108).addBox(-2.25F, -14.5F, -10.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(105, 108).addBox(0.25F, -14.5F, -10.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone167 = south_left_side.addOrReplaceChild("bone167", CubeListBuilder.create().texOffs(105, 95).addBox(-1.0F, -3.25F, -1.0F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(105, 108).addBox(-1.5F, -3.75F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition large_lever3_control = south_left_side.addOrReplaceChild("large_lever3_control", CubeListBuilder.create().texOffs(0, 123).addBox(-2.75F, -1.0F, -5.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 123).addBox(0.75F, -1.0F, -5.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(90, 44).addBox(-2.25F, -1.0F, -5.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 44).addBox(1.25F, -1.0F, -5.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(100, 31).addBox(-1.5F, -1.0F, -5.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.5F, -10.8F, -0.6109F, 0.0F, 0.0F));

        PartDefinition south = controls.addOrReplaceChild("south", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone176 = south.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(105, 95).addBox(-1.0F, -3.25F, -1.0F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(36, 50).addBox(-1.5F, -6.9F, 9.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition large_lever2_control = bone176.addOrReplaceChild("large_lever2_control", CubeListBuilder.create().texOffs(24, 131).addBox(0.15F, -2.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.35F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, -6.9F, 10.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition monitor_control = south.addOrReplaceChild("monitor_control", CubeListBuilder.create().texOffs(150, 96).addBox(-1.0F, -4.25F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(134, 79).addBox(-1.0F, -2.25F, -8.0F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(143, 91).addBox(-3.0F, -10.75F, -7.5F, 1.0F, 7.0F, 7.0F, new CubeDeformation(2.0F)), PartPose.offset(0.0F, -6.5F, -18.3F));

        PartDefinition south_right_side = controls.addOrReplaceChild("south_right_side", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone178 = south_right_side.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(105, 95).addBox(-1.0F, -2.75F, -4.75F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(17, 114).addBox(-1.0F, -7.4F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone179 = bone178.addOrReplaceChild("bone179", CubeListBuilder.create().texOffs(10, 0).addBox(-0.25F, -1.95F, -0.375F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 115).addBox(-0.25F, -2.95F, -0.375F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 87).addBox(2.75F, -2.95F, -0.375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 50).addBox(2.75F, 4.05F, 0.625F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -7.45F, 10.375F, -0.2618F, 0.0F, 0.0F));

        PartDefinition north_left_side = controls.addOrReplaceChild("north_left_side", CubeListBuilder.create().texOffs(121, 91).addBox(-2.5F, -13.5F, -13.05F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(105, 108).addBox(-2.25F, -14.5F, -10.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(105, 108).addBox(0.25F, -14.5F, -10.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone154 = north_left_side.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(105, 95).addBox(-1.75F, -3.25F, -1.0F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(99, 95).addBox(-1.75F, -2.5F, -3.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(126, 19).addBox(0.75F, -3.0F, 1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -20.3F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone156 = bone154.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(14, 34).addBox(-0.75F, -0.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -3.0F, 1.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition bone157 = bone154.addOrReplaceChild("bone157", CubeListBuilder.create().texOffs(14, 34).addBox(-0.75F, -0.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -3.0F, 3.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition bone158 = bone154.addOrReplaceChild("bone158", CubeListBuilder.create().texOffs(14, 34).addBox(-0.75F, -0.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -3.0F, 5.5F, -0.5672F, 0.0F, 0.0F));

        PartDefinition large_lever_control_throttle = north_left_side.addOrReplaceChild("large_lever_control_throttle", CubeListBuilder.create().texOffs(0, 123).addBox(-2.75F, -1.0F, -5.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 123).addBox(0.75F, -1.0F, -5.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(90, 44).addBox(-2.25F, -1.0F, -5.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 44).addBox(1.25F, -1.0F, -5.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(100, 31).addBox(-1.5F, -1.0F, -5.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.5F, -10.8F, -0.6109F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        base_control.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        controls.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return root;
    }


    @Override
    public void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().getAllParts().forEach(ModelPart::resetPose);
        TardisClientData reactions = TardisClientData.getInstance(level.dimension());

        this.animate(reactions.ROTOR_ANIMATION, MODEL_FLIGHT_LOOP, Minecraft.getInstance().player.tickCount);

        this.throttle.xRot = (reactions.isThrottleDown()) ? -25f : -32f;

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(getTexture(globalConsoleBlock)));

        base_control.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        controls.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

        if (globalConsoleBlock == null) return;

        if (globalConsoleBlock.pattern().name().equals("purple")) {
            core.root().getAllParts().forEach(ModelPart::resetPose);
            poseStack.pushPose();
            poseStack.translate(0, -1.5F, 0);

            double amplitude = 0.2;
            double frequency = 0.05;
            double y = amplitude * Math.sin(frequency * Minecraft.getInstance().player.tickCount) / 2;

            poseStack.translate(0, reactions.isFlying() ? y * 2 : y, 0);
            core.animate(reactions.ROTOR_ANIMATION, CORE, Minecraft.getInstance().player.tickCount, 0.1F);
            core.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/crystal/crystal_core.png"))), packedLight, packedOverlay, red, green, blue, alpha / 2);
            poseStack.popPose();
        }

    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return CRYSTAL_TEXTURE;
    }
}
