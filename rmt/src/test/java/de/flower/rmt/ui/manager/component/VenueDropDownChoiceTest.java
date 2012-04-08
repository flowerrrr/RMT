package de.flower.rmt.ui.manager.component;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.panel.Panel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class VenueDropDownChoiceTest extends AbstractWicketUnitTests {

    @Test
    public void testEscapeModelString() {
        wicketTester.startComponentInPage(new TestPanel());
        wicketTester.dumpComponentWithPage();
        wicketTester.assertContains("&lt;unknown&gt;");
    }

    private static class TestPanel extends Panel {

        public TestPanel() {
            super("panel");
            add(new VenueDropDownChoice("select") {
                @Override
                protected String getNullValidKey() {
                    return "<unknown>";
                }
            });
        }

        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><select wicket:id='select'/></wicket:panel>");
        }
    }
}
