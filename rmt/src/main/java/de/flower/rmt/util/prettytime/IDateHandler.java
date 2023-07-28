package de.flower.rmt.util.prettytime;

import java.util.Date;


public interface IDateHandler {

    String format(Date date, final long delta);

    /**
     * 
     * @param date
     * @param delta positive when date lies before reference date
     * @return
     */
    boolean canHandle(Date date, long delta);
}
