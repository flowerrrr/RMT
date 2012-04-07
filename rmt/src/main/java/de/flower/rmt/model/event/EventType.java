package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;

/**
 * @author flowerrrr
 */
public enum EventType {

    Event(Event.class, false),
    Match(Match.class, true),
    Training(Training.class, true),
    Tournament(Tournament.class, true);

    private Class<? extends Event> clazz;

    private boolean hasSurface;

    private EventType(Class<? extends Event> clazz, boolean hasSurface) {
        this.clazz = clazz;
        this.hasSurface = hasSurface;
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

    public Event newInstance(final Club club) {
        switch (this) {
            case Event:
                return new Event(club);
            case Match:
                return new Match(club);
            case Training:
                return new Training(club);
            case Tournament:
                return new Tournament(club);
            default:
                throw new RuntimeException();
        }
    }

    public static boolean isMatch(final Event event) {
        return event.getClass().equals(Match.clazz);
    }

    public static boolean hasSurface(final Event event) {
        return EventType.from(event).hasSurface;
    }
}
