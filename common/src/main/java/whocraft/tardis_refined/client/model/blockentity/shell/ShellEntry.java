package whocraft.tardis_refined.client.model.blockentity.shell;

import whocraft.tardis_refined.client.model.blockentity.door.interior.ShellDoorModel;

public class ShellEntry {

    private ShellModel shellModel;
    private ShellDoorModel shellDoorModel;

    public ShellEntry(ShellModel shellModel, ShellDoorModel shellDoorModel) {
        this.shellModel = shellModel;
        this.shellDoorModel = shellDoorModel;
    }

    public ShellModel getShellModel() {
        return shellModel;
    }

    public void setShellModel(ShellModel shellModel) {
        this.shellModel = shellModel;
    }

    public ShellDoorModel getShellDoorModel() {
        return shellDoorModel;
    }

    public void setShellDoorModel(ShellDoorModel shellDoorModel) {
        this.shellDoorModel = shellDoorModel;
    }
}
