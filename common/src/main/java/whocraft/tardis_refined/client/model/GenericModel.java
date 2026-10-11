package whocraft.tardis_refined.client.model;

import whocraft.tardis_refined.client.model.TardisModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class GenericModel extends TardisModel {

    private final ModelPart root;

    public GenericModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}