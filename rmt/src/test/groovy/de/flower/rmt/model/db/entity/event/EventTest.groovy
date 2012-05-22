package de.flower.rmt.model.db.entity.event

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.testng.annotations.Test
import static org.testng.Assert.*

/**
 * @author flowerrrr
 */
public class EventTest {

    @Test
    public void testDateHandling() {
        def event = new Event();

        assertTrue(event.getDate() == null)
        assertTrue(event.getTime() == null)
        assertTrue(event.getDateTime() == null)
        assertTrue(event.getDateTimeAsDate() == null)

        def date = new Date();
        def time = new LocalTime();
        event.setDate(date);
        assertEquals(event.getDate(), new LocalDate(date).toDate())
        assertTrue(event.getTime() == null)

        event.setDate(null)
        assertNull(event.getDateTime())
        event.setTime(time)
        assertTrue(event.getDate() == null)
        assertEquals(event.getTime(), time)
        assertEquals(event.getDateTime(), new DateTime(0).withFields(time))

        event.setDate(date)
        assertEquals(event.getDate(), new LocalDate(date).toDate())
        assertEquals(event.getTime(), time)
        assertEquals(event.getDateTime(), new DateTime(date).withFields(time))

        def dateTime = new DateTime(2012, 1, 1, 12, 00)
        event.setDateTime(dateTime)
        assertEquals(event.getDate(), new LocalDate(2012, 1, 1).toDate())
        assertEquals(event.getTime(), new LocalTime(12, 00))

    }
}
