package de.flower.rmt.model.event;

/**
 * @author flowerrrr
 */
public enum EventType {

    Event(Event.class),
    Match(Match.class),
    Training(Training.class),
    Tournament(Tournament.class);

    private Class<? extends Event> clazz;

    private EventType(Class<? extends Event> clazz) {
        this.clazz = clazz;
    }

    public String getResourceKey() {
        return "event.type." + this.name().toLowerCase();
    }

    public static EventType from(Event event) {
        for (EventType type : values()) {
            // must match by class. instance of does not work because of class hierarchy used for events
            if (type.clazz.equals(event.getClass())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown eventType [" + event.getClass().getSimpleName() + "]");
    }

    public Class<? extends Event> getClazz() {
        return this.clazz;
    }
}
