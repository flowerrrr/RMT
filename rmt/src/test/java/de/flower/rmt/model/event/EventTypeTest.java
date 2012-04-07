package de.flower.rmt.model.event;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class EventTypeTest {

    @Test
    public void testIsSoccerEvent() {
        Match match = new Match();
        assertTrue(EventType.isSoccerEvent(match));
    }

}
