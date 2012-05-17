package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;

/**
 * @author flowerrrr
 */
public enum EventType {

    Event(Event.class, 0),
    Match(Match.class, 45),
    Training(Training.class, 15),
    Tournament(Tournament.class, 45);

    private Class<? extends Event> clazz;

    private int meetBeforeKickOffMinutes;

    private EventType(Class<? extends Event> clazz, int meetBeforeKickOffMinutes) {
        this.clazz = clazz;
        this.meetBeforeKickOffMinutes = meetBeforeKickOffMinutes;
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

    public boolean isMatch() {
        return this == Match;
    }

    public boolean isSoccerEvent() {
        return this == Match || this == Tournament  || this == Training;
    }

    public static boolean isMatch(final Event event) {
        return event.getClass().equals(Match.clazz);
    }

    public static boolean isSoccerEvent(final Event event) {
        return event instanceof AbstractSoccerEvent;
    }

    public int getMeetBeforeKickOffMinutes() {
        return meetBeforeKickOffMinutes;
    }
}
