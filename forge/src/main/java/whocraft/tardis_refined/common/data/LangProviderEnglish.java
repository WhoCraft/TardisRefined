package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;

public class LangProviderEnglish extends LanguageProvider {

    public LangProviderEnglish(DataGenerator gen) {
        super(gen, TardisRefined.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockRegistry.ARS_EGG.get(), "ARS Egg");
        add(BlockRegistry.ARS_LEAVES.get(), "ARS Leaves");
        add(BlockRegistry.ARS_LEAVES_FENCE.get(), "ARS Fence");
        add(BlockRegistry.ARS_LEAVES_SLAB.get(), "ARS Slab");
        add(BlockRegistry.BULK_HEAD_DOOR.get(), "Bulk Head Door");
        add(BlockRegistry.ROOT_PLANT_BLOCK.get(), "Root Plant");
        add(BlockRegistry.ROOT_SHELL_BLOCK.get(), "Root Shell");
        add(BlockRegistry.TERRAFORMER_BLOCK.get(), "Terraformer");

        add(EntityRegistry.CONTROL_ENTITY.get(), "Generic Control");

        addControl(ConsoleControl.DOOR_TOGGLE, "Door Toggle");
        addControl(ConsoleControl.X, "X");
        addControl(ConsoleControl.Y, "Y");
        addControl(ConsoleControl.Z, "Z");
        addControl(ConsoleControl.INCREMENT, "Increment");
        addControl(ConsoleControl.ROTATE, "Direction");
        addControl(ConsoleControl.RANDOM, "Randomizer");
        addControl(ConsoleControl.THROTTLE, "Throttle");

    }

    public void addControl(ConsoleControl control, String name){
        add(control.getLangId(), name);
    }

}
