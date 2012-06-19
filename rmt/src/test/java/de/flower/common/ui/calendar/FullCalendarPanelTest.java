package de.flower.common.ui.calendar;

import com.google.common.collect.Lists;
import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.rmt.model.db.entity.CalItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.testng.annotations.Test;

import java.util.Date;
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
        protected List<CalItem> loadCalItems(final Date start, final Date end) {
            return Lists.newArrayList();
        }

        @Override
        protected void onEdit(final AjaxRequestTarget target, final long id) {
        }
    }
}
