package whocraft.tardis_refined.client.model.blockentity.shell;

import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;
import whocraft.tardis_refined.patterns.ShellPattern;

public class ShellEntry {

    private ShellModel shellModel;
    private ShellDoorModel shellDoorModel;

    public ShellEntry(ShellModel shellModel, ShellDoorModel shellDoorModel) {
        this.shellModel = shellModel;
        this.shellDoorModel = shellDoorModel;
    }

    public ShellModel getShellModel(ShellPattern shellPattern) {
        return shellModel;
    }

    public ShellDoorModel getShellDoorModel(ShellPattern shellPattern) {
        return shellDoorModel;
    }

}
