package whocraft.tardis_refined.common.capability.player.fabric;

import org.ladysnake.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

import java.util.Objects;
import java.util.Optional;

public class TardisPlayerInfoImpl extends TardisPlayerInfo implements ComponentV3 {

    public TardisPlayerInfoImpl(Player player) {
        super(player);
    }

    public static Optional<TardisPlayerInfo> get(LivingEntity livingEntity) {
        try {
            return Optional.of(TardisPlayerComponents.TARDIS_PLAYER_INFO.get(livingEntity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void readData(ValueInput input) {
        input.read("tardis_refined_data", CompoundTag.CODEC).ifPresent(this::loadData);
    }

    @Override
    public void writeData(ValueOutput output) {
        output.store("tardis_refined_data", CompoundTag.CODEC, this.saveData());
    }


}
