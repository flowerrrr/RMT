package de.flower.common.ui.calendar;

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
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void onEdit(final AjaxRequestTarget target, final CalEvent calEvent) {
        }
    }
}
