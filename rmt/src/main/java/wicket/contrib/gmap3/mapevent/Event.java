package wicket.contrib.gmap3.mapevent;

/**
 * Constants for events.
 */
public enum Event {

    BOUNDS_CHANGED,
    CENTER_CHANGED,
    CLICK,
    DBL_CLICK,
    DRAG,
    DRAGEND,
    DRAGSTART,
    HEADING_CHANGED,
    IDLE,
    MAPTYPEID_CHANGED,
    MOUSEMOVE,
    MOUSEOUT,
    MOUSEOVER,
    PROJECTION_CHANGED,
    RESIZE,
    RIGHTCLICK,
    TILESLOADED,
    TILT_CHANGED,
    ZOOM_CHANGED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
