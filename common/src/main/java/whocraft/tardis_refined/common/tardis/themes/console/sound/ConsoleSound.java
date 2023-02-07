package whocraft.tardis_refined.common.tardis.themes.console.sound;

import javax.annotation.Nullable;


/**
 * Sound for a specific action, featuring left, right and failure presses.
 **/
public class ConsoleSound {

    private final PitchedSound leftClick;
    private final PitchedSound rightClick;

    /**
     * Datatype for referencing a sound event for how a player would interact with a control.
     * @param leftClick the sound event fired when a control is left-clicked.;
     * @param rightClick the sound event fired when a control is right-clicked;
     * */
    public ConsoleSound(@Nullable PitchedSound leftClick, @Nullable PitchedSound rightClick) {
        this.leftClick = leftClick;
        this.rightClick = rightClick;
    }

    public PitchedSound getLeftClick() {
        return leftClick;
    }


    public PitchedSound getRightClick() {
        return rightClick;
    }
}
