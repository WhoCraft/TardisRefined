package whocraft.tardis_refined.common.tardis.control;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRControlRegistry;


public class ControlSpecification {
    private Control control;
    private Vector3f offsetPosition;
    private EntityDimensions size;

    private static final Codec<EntityDimensions> DIMENSIONS_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("width").forGetter(d -> d.width),
                    Codec.FLOAT.fieldOf("height").forGetter(d -> d.height),
                    Codec.BOOL.fieldOf("fixed").forGetter(d -> d.fixed)
            ).apply(instance, EntityDimensions::new)
    );

    public static final Codec<ControlSpecification> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.xmap(
                            TRControlRegistry::get, TRControlRegistry::getKey
                    ).fieldOf("control").forGetter(ControlSpecification::control),
                    ExtraCodecs.VECTOR3F.fieldOf("offset_position").forGetter(ControlSpecification::offsetPosition),
                    DIMENSIONS_CODEC.fieldOf("size").forGetter(ControlSpecification::size)
            ).apply(instance, ControlSpecification::new)
    );

    public ControlSpecification(Control control, Vector3f offsetPosition, EntityDimensions size) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.size = size;
    }

    public ControlSpecification(RegistrySupplier<Control> controlRegistrySupplier, Vector3f offsetPosition, EntityDimensions size) {
        this(controlRegistrySupplier.get(), offsetPosition, size);
    }

    public Control control() {
        return control;
    }

    public ControlSpecification setControl(Control control) {
        this.control = control;
        return this;
    }

    public Vector3f offsetPosition() {
        return offsetPosition;
    }

    public ControlSpecification setOffsetPosition(Vector3f offsetPosition) {
        this.offsetPosition = offsetPosition;
        return this;
    }

    public EntityDimensions size() {
        return size;
    }

    public ControlSpecification setSize(EntityDimensions size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ControlSpecification specification) {
            if (control == specification.control && offsetPosition.equals(specification.offsetPosition)) {
                return size.width == specification.size.width && size.height == specification.size.height && size.fixed == specification.size.fixed;
            }
        }
        return false;
    }
}