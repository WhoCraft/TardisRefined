package whocraft.tardis_refined.compat.portals26;

/** Client fallback while the optional portal renderer is being ported. */
public final class ImmersivePortalsClient {
    private ImmersivePortalsClient() {}

    public static boolean shouldStopRenderingInPortal() { return false; }
    public static void doClientRenderers() {}
}
