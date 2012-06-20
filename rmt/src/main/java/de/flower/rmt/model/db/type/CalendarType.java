package de.flower.rmt.model.db.type;

/**
* @author flowerrrr
*/
public enum CalendarType {
    /**
     * users own personal cal items.
     */
    USER,
    /**
     * matches, trainings an other events of the club.
     */
    CLUB,
    /**
     * personal calendars of all other users.
     */
    OTHERS;

    public static String getResourceKey(CalendarType object) {
        return "calendar.enum." + object.name().toLowerCase();
    }

}
