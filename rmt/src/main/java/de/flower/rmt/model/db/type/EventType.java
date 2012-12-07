package de.flower.rmt.model.db.type;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.event.AbstractSoccerEvent;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.db.entity.event.Tournament;
import de.flower.rmt.model.db.entity.event.Training;

/**
 * @author flowerrrr
 *
 * NOTE: Do not move, it will break the activity feed as persisted messages cannot be deserialized again.
 */
public enum EventType {

    Event(de.flower.rmt.model.db.entity.event.Event.class, 0, 4*60 /* only a guess */),
    Match(de.flower.rmt.model.db.entity.event.Match.class, 45, 105),
    Training(de.flower.rmt.model.db.entity.event.Training.class, 15, 90),
    Tournament(de.flower.rmt.model.db.entity.event.Tournament.class, 45, 6 * 60);

    private Class<? extends Event> clazz;

    private int meetBeforeKickOffMinutes;

    private int durationMinutes;

    private EventType(Class<? extends Event> clazz, int meetBeforeKickOffMinutes, int durationMinutes) {
        this.clazz = clazz;
        this.meetBeforeKickOffMinutes = meetBeforeKickOffMinutes;
        this.durationMinutes = durationMinutes;
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

    public int getDurationMinutes() {
        return durationMinutes;
    }

}
