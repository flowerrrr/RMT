package de.flower.rmt.util;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class DatesTest {

    @Test
    public void testFormat() {
        Date date = new DateTime(2000, 4, 6, 10, 3, 59).toDate();
        assertEquals(Dates.formatDateTimeMedium(date), "06.04.2000 10:03:59");
        assertEquals(Dates.formatDateLong(date), "6. April 2000");
        assertEquals(Dates.formatDateMedium(date), "06.04.2000");
        assertEquals(Dates.formatTimeMedium(date), "10:03:59");
        assertEquals(Dates.formatTimeShort(date), "10:03");
        assertEquals(Dates.formatTimeShort(new LocalTime(3, 45)), "03:45");
    }
}
