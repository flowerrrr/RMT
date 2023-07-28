package de.flower.rmt.util.prettytime;

import static org.testng.Assert.assertEquals;

import java.util.Locale;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


public class PrettyTimeTest {

    private final static Logger log = LoggerFactory.getLogger(PrettyTimeTest.class);

    @Test
    public void testDateHandler() {
        // use fixed date to reproduce test results
        int hourOfDay = 22;
        DateTime now = new DateTime(2012, 05, 1, hourOfDay, 0, 0);

        testDH(AbstractDateHandler.JustNow.class, now.plusMillis(1), now);

        testDH(AbstractDateHandler.Minute.class, now.minusMinutes(1), now);
        testDH(AbstractDateHandler.Minute.class, now.minusMinutes(59), now);

        testDH(AbstractDateHandler.Hour.class, now.minusMinutes(60), now);
        testDH(AbstractDateHandler.Hour.class, now.minusHours(1), now);
        testDH(AbstractDateHandler.Hour.class, now.minusHours(12), now);

        testDH(AbstractDateHandler.Today.class, now.minusHours(13), now);
        testDH(AbstractDateHandler.Today.class, now.minusHours(hourOfDay), now);

        testDH(AbstractDateHandler.Yesterday.class, now.minusHours(24), now);
        testDH(AbstractDateHandler.Yesterday.class, now.minusHours(24 + hourOfDay), now);
        testDH(AbstractDateHandler.Week.class, now.minusHours(24 + hourOfDay + 1), now);

        testDH(AbstractDateHandler.Week.class, now.minusDays(2), now);
        testDH(AbstractDateHandler.Week.class, now.minusDays(6), now);
        testDH(AbstractDateHandler.Default.class, now.minusDays(7), now);
    }

    private void testDH(Class<? extends IDateHandler> expectedClass, DateTime dateTime, DateTime reference) {
        PrettyTime pt = new PrettyTime(reference.toDate());
        assertEquals(pt.getDateHandler(dateTime.toDate()).getClass(), expectedClass);
    }

    @Test
    public void testFormat() {
        Locale.setDefault(Locale.GERMAN);
        
        int hourOfDay = 22;
        DateTime now = new DateTime(2012, 05, 1, hourOfDay, 0, 0); // tuesday

        test("Soeben", now.plusMillis(1), now);

        test("Vor 1 Minute", now.minusMinutes(1), now);
        test("Vor 59 Minuten", now.minusMinutes(59), now);

        test("Vor 1 Stunde", now.minusMinutes(60), now);
        test("Vor 1 Stunde", now.minusHours(1), now);
        test("Vor 12 Stunden", now.minusHours(12), now);

        test("Heute um 09:00", now.minusHours(13), now);
        test("Heute um 00:00", now.minusHours(hourOfDay), now);

        test("Gestern um 22:00", now.minusHours(24), now);
        test("Gestern um 00:00", now.minusHours(24 + hourOfDay), now);
        test("Sonntag um 23:00", now.minusHours(24 + hourOfDay + 1), now);

        test("Sonntag um 22:00", now.minusDays(2), now);
        test("Mittwoch um 22:00", now.minusDays(6), now);
        test("24. April 2012 um 22:00", now.minusDays(7), now);
    }

    private void test(String expected, DateTime dateTime, DateTime reference) {
        PrettyTime pt = new PrettyTime(reference.toDate());
        String sDate = pt.format(dateTime.toDate());
        log.info(sDate);
        assertEquals(sDate, expected);
    }
}
