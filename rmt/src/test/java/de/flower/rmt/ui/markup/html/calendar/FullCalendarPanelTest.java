package de.flower.rmt.ui.markup.html.calendar;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author flowerrrr
 */
public class FullCalendarPanelTest extends AbstractWicketUnitTests {

    @Test
    public void testGson() {
        wicketTester.startComponentInPage(new TestFullCalendarPanel());
        wicketTester.dumpComponentWithPage();
    }

    public static class TestFullCalendarPanel extends FullCalendarPanel {

        @Override
        protected List<CalEvent> loadCalEvents(final DateTime start, final DateTime end) {
            return null;
        }

        @Override
        protected void onEventClick(final AjaxRequestTarget target, final CalEvent calEvent) {
        }
    }
}
