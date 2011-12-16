package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;

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
}
