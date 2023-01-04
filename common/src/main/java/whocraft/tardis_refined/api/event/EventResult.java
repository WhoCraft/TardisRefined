package whocraft.tardis_refined.api.event;

public class EventResult {

    private static final EventResult CANCEL = new EventResult(true, true);
    private static final EventResult CANCEL_AND_CONTINUE = new EventResult(true, false);
    private static final EventResult STOP_LISTENERS = new EventResult(false, true);
    private static final EventResult PASS = new EventResult(false, false);

    private final boolean cancelEvent;
    private final boolean stopListeners;

    private EventResult(boolean cancelEvent, boolean stopListeners) {
        this.cancelEvent = cancelEvent;
        this.stopListeners = stopListeners;
    }

    public boolean cancelsEvent() {
        return this.cancelEvent;
    }

    public boolean stopsListeners() {
        return this.stopListeners;
    }

    /**
     * @return Cancel the event and prevent further listeners from being executed
     */
    public static EventResult cancel() {
        return CANCEL;
    }

    /**
     * @return Mark event as cancelled but still execute next listeners
     */
    public static EventResult cancelAndContinue() {
        return CANCEL_AND_CONTINUE;
    }

    /**
     * @return Does not cancel event but stops next listeners from being executed
     */
    public static EventResult stopListeners() {
        return STOP_LISTENERS;
    }

    /**
     * @return Does not intervene in the event or the execution of other listeners
     */
    public static EventResult pass() {
        return PASS;
    }

}
