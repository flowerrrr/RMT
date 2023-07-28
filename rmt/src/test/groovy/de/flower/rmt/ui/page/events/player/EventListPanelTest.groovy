package de.flower.rmt.ui.page.events.player

import de.flower.rmt.model.db.entity.event.Event
import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.testng.annotations.Test

import static org.testng.Assert.assertFalse
import static org.testng.Assert.assertTrue


@SuppressWarnings("GroovyAccessibility")
public class EventListPanelTest {

    @Test
    public void testNextEvent() {
        def eventPast = new Event(date: new DateTime().minusDays(1).toDate(), time: new LocalTime(12, 0))
        def eventToday = new Event(date: new Date(), time: new LocalTime().plusMinutes(1))
        def eventToday2 = new Event(date: new Date(), time: new LocalTime().plusHours(4))
        def eventFuture1 = new Event(date: new DateTime().plusDays(1).toDate(), time: new LocalTime(20, 0))
        def eventFuture2 = new Event(date: new DateTime().plusDays(1).toDate(), time: new LocalTime(20, 0))
        def list = [eventPast, eventToday, eventToday2, eventFuture1, eventFuture2]
        // event in the past
        assertFalse(EventListPanel.isNextEvent(eventPast, list))
        assertTrue(EventListPanel.isNextEvent(eventToday, list))
        assertFalse(EventListPanel.isNextEvent(eventToday2, list))
        assertFalse(EventListPanel.isNextEvent(eventFuture1, list))
    }
}
