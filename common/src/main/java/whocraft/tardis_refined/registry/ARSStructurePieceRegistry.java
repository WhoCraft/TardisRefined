package whocraft.tardis_refined.registry;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.chunk.ARSStructurePiece;

import java.util.ArrayList;
import java.util.List;

public class ARSStructurePieceRegistry {

    public static List<ARSStructurePiece> CORRIDORS = new ArrayList<>();
    public static List<ARSStructurePiece> ROOMS = new ArrayList<>();

    public static void register() {

        registerCorridors();
        registerRooms();

    }

    public static void registerCorridors() {
        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r16")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r18")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r21")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r25")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r27")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r31")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r35")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r38")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r42")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r66")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r69")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r7")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r71")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r76")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r78")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r8")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r9")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r97")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r17")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r33")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r816")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r88")));
//        registerCorridorPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r98")));
    }
    public static void registerRooms() {
        registerRoomPiece(new ARSStructurePiece(createCorridorResourceLocation("gs_r16")));

//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r282")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r308")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r389")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r390")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r391")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r412")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r564")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r568")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r66")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r705")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r744")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r745")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r752")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r790")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r832")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r840")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r889")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r916")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r946")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r971")));
//        registerRoomPiece(new ARSStructurePiece(createRoomResourceLocation("gs_r999")));
    }


    /**
     * Register a new corridor to be featured in corridor generation.
     * @param corridorPiece ARSStructurePiece to be placed.
     */

    private static void registerCorridorPiece(ARSStructurePiece corridorPiece) {
        CORRIDORS.add(corridorPiece);
    }

    /**
     * Register a new room to be featured in corridor generation.
     * @param roomPiece ARSStructurePiece to be placed.
     */

    private static void registerRoomPiece(ARSStructurePiece roomPiece) {
        ROOMS.add(roomPiece);
    }

    private static ResourceLocation createCorridorResourceLocation(String id) {
        return new ResourceLocation(TardisRefined.MODID, "corridors/" + id);
    }

    private static ResourceLocation createRoomResourceLocation(String id) {
        return new ResourceLocation(TardisRefined.MODID, "rooms/" + id);
    }



}
