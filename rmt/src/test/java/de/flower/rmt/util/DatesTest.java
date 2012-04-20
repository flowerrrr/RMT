package de.flower.rmt.util;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class DatesTest {

    private final static Logger log = LoggerFactory.getLogger(DatesTest.class);

    @Test
    public void testFormat() {
        Date date = new DateTime(2000, 5, 6, 10, 3, 59).toDate();
        assertEquals(Dates.formatDateTimeMedium(date), "06.05.2000 10:03:59");
        assertEquals(Dates.formatDateLong(date), "6. Mai 2000");
        assertEquals(Dates.formatDateMedium(date), "06.05.2000");
        assertEquals(Dates.formatDateShort(date), "06.05.");
        assertEquals(Dates.formatTimeMedium(date), "10:03:59");
        assertEquals(Dates.formatTimeShort(date), "10:03");
        assertEquals(Dates.formatTimeShort(new LocalTime(3, 45)), "03:45");
    }

    @Test
    public void testFormatFacebook() {
        test("Vor einem Augenblick", new Date());
    }

    private void test(String expected, Date date) {
        String sDate = Dates.formatFacebook(date);
        log.info(sDate);
        assertEquals(sDate, expected);
    }
}

