package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.*;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;


public enum ConsoleTheme implements StringRepresentable {

    FACTORY("factory", new FactoryConsoleTheme()),
    CRYSTAL("crystal", new CrystalConsoleTheme()),
    COPPER("copper", new CopperConsoleTheme()),
    CORAL("coral", new CoralConsoleTheme()),
    TOYOTA("toyota", new ToyotaConsoleTheme()),
    VICTORIAN("victorian", new VictorianConsoleTheme()),
    MYST("myst", new MystConsoleTheme()),
    NUKA("nuka", new NukaConsoleTheme()),
    INITIATIVE("initiative", new InitiativeConsoleTheme());

    private final String id;
    private final ConsoleThemeDetails consoleThemeDetails;

    ConsoleTheme(String id, ConsoleThemeDetails consoleThemeDetails) {
        this.id = id;
        this.consoleThemeDetails = consoleThemeDetails;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return consoleThemeDetails.getControlSpecification();
    }

    public ConsoleSoundProfile getSoundProfile() {
        return consoleThemeDetails.getSoundProfile();
    }

    private static final ConsoleTheme[] vals = values();

    public ConsoleTheme next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
