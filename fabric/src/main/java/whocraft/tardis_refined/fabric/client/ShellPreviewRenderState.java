package whocraft.tardis_refined.fabric.client;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

/** GeckoLib data carrier for the shell preview's immutable extracted entity state. */
public final class ShellPreviewRenderState extends EntityRenderState implements GeoRenderState {
    private final Map<DataTicket<?>, Object> geckolibData = new HashMap<>();
    public boolean vortexOnly;
    public boolean vortexBackgroundPreview;
    public Identifier vortexPreviewType;
    public int vortexViewportWidth;
    public int vortexViewportHeight;
    public final Quaternionf shellFlightRotation = new Quaternionf();
    public final Vector3f shellFlightOffset = new Vector3f();

    @Override public Map<DataTicket<?>, Object> getDataMap() { return geckolibData; }

    public void setShell(Identifier theme, Identifier pattern) {
        addGeckolibData(whocraft.tardis_refined.client.model.blockentity.shell.ThemeShellGeoModel.SHELL_THEME, theme);
        addGeckolibData(whocraft.tardis_refined.client.model.blockentity.shell.ThemeShellGeoModel.SHELL_PATTERN, pattern);
    }
}
