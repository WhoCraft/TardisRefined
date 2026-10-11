package whocraft.tardis_refined.api.event;

import net.minecraft.resources.Identifier;

/**
 * Object to identify the source of Shell updates.
 */
public class ShellChangeSource {

    private Identifier id;

    public ShellChangeSource(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }
}
