package whocraft.tardis_refined.common.util;

import net.minecraft.server.MinecraftServer;

import java.util.Collection;

public class Platform {

    public static boolean isProduction(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.isProduction();
    }

    public static boolean isModLoaded(String id){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.isModLoaded(id);
    }

    public static Collection<String> getModIds(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.getModIds();
    }

    public static boolean isClient(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.isClient();
    }

    public static boolean isServer(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.isServer();
    }

    public static boolean isForge(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.isForge();
    }

    public static MinecraftServer getServer(){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.getServer();
    }


    public static String getModName(String namespace){
        return whocraft.tardis_refined.common.util.fabric.PlatformImpl.getModName(namespace);
    }

}