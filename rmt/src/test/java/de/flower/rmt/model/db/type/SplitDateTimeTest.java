package de.flower.rmt.model.db.type;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class SplitDateTimeTest {

    private final static Logger log = LoggerFactory.getLogger(SplitDateTimeTest.class);

    @Test
    public void testGetterSetter() {
        SplitDateTime splitDateTime = new SplitDateTime();
        DateTime dt = new DateTime();

        splitDateTime.setDate(dt.toDate());
        log.info(splitDateTime.toString());
        assertEquals(splitDateTime.getDate(), new LocalDate(dt).toDate());

        LocalTime time = new LocalTime(2, 15);
        splitDateTime.setTime(time);
        log.info(splitDateTime.toString());
        assertEquals(splitDateTime.getTime(), time);

        splitDateTime = new SplitDateTime();
        splitDateTime.setTime(time);
        log.info(splitDateTime.toString());

        splitDateTime = new SplitDateTime(dt);
        log.info(splitDateTime.toString());
        assertEquals(splitDateTime.getTime(), dt.toLocalTime());
    }
}
