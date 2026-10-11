package whocraft.tardis_refined.common.world.chunk;

import net.minecraft.resources.Identifier;

public class ARSStructurePiece {

    public static int LOCKED_PIECE_CHUNK_SIZE = 3;

    // Resource location defined in constructor.
    private Identifier resourceLocation;

    /**
     * 3x3 chunk structure used to place infinite corridor generation.
     *
     * @param resourceLocation Identifier of the structure file
     */
    public ARSStructurePiece(Identifier resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    /**
     * Fetch the resource location of the piece
     *
     * @return resource location of the piece
     */
    public Identifier getIdentifier() {
        return resourceLocation;
    }


}
