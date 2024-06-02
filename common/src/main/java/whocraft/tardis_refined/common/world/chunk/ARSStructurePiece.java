package whocraft.tardis_refined.common.world.chunk;

import net.minecraft.resources.ResourceLocation;

public class ARSStructurePiece {

    public static int LOCKED_PIECE_CHUNK_SIZE = 3;

    // Resource location defined in constructor.
    private ResourceLocation resourceLocation;

    /**
     * 3x3 chunk structure used to place infinite corridor generation.
     *
     * @param resourceLocation ResourceLocation of the structure file
     */
    public ARSStructurePiece(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    /**
     * Fetch the resource location of the piece
     *
     * @return resource location of the piece
     */
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }


}
