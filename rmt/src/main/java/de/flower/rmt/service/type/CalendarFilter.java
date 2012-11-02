package de.flower.rmt.service.type;

import de.flower.rmt.model.db.entity.Team;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class CalendarFilter implements Serializable {

    public static final CalendarFilter USER = new CalendarFilter(Type.USER);

    public static final CalendarFilter CLUB = new CalendarFilter(Type.CLUB);

    public static final CalendarFilter OTHERS = new CalendarFilter(Type.OTHERS);

    public enum Type {
        /**
         * users own personal cal items.
         */
        USER,
        /**
         * matches, trainings an other events of the club.
         */
        CLUB,
        /**
         * personal calendars of all users belonging to a team. which team is defined in CalendarFilter.
         */
        TEAM,
        /**
         * personal calendars of all other users.
         */
        OTHERS;

        public static String getResourceKey(Type object) {
            return "calendar.enum." + object.name().toLowerCase();
        }
    }

    public Type type;

    /**
     * Set if type == TEAM.
     */
    public transient Team team;

    public CalendarFilter(final Type type) {
        this.type = type;
    }

    public CalendarFilter(final Type type, final Team team) {
        this.type = type;
        this.team = team;
    }


}
