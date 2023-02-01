package whocraft.tardis_refined.common.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TRParticles;

import java.io.IOException;
import java.nio.file.Path;

public class ParticleProvider implements DataProvider {

    private DataGenerator gen;

    public ParticleProvider(DataGenerator gen) {
        this.gen = gen;
    }

    @Override
    public void run(CachedOutput arg) throws IOException {
        Path base = gen.getOutputFolder();

        makeParticle(TRParticles.GALLIFREY.get(), new ResourceLocation(TardisRefined.MODID, "gallifrey"), 5, arg, base);

    }

    public void makeParticle(ParticleType<?> type, ResourceLocation textureName, int count, CachedOutput cache, Path base) throws IOException {
        DataProvider.saveStable(cache, this.createParticle(textureName, count), getPath(base, ForgeRegistries.PARTICLE_TYPES.getKey(type)));
    }

    public static Path getPath(Path base, ResourceLocation name) {
        return base.resolve("assets/" + name.getNamespace() + "/particles/" + name.getPath() + ".json");
    }

    public JsonObject createParticle(ResourceLocation baseName, int max){
        JsonObject root = new JsonObject();

        JsonArray textures = new JsonArray();

        for(int i = 0; i < max; ++i) {
            textures.add(baseName.getNamespace() + ":" + baseName.getPath() + i);
        }

        root.add("textures", textures);

        return root;
    }


    @Override
    public String getName() {
        return "Particles";
    }
}
