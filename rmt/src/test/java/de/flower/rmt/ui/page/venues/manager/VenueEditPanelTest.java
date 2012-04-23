package de.flower.rmt.ui.page.venues.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.VenueModel;
import org.apache.wicket.Component;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class VenueEditPanelTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startComponentInPage(new VenueEditPanel(new VenueModel()));
        wicketTester.dumpComponentWithPage();
    }

    @Test
    public void testGeocoding() {
        // verify that google delivers correct address.
        String searchAddress = "Werner Heisenberg Allee  25\n 80939 Muenchen";
        // TODO (flowerrrr - 23.04.12) use umlaut again once upgraded to gradle 1.0 (http://issues.gradle.org/browse/GRADLE-1618)
        String resultAddress = "Werner-Heisenberg-Allee 25, 80939 M";
        String resultAddress2 = "nchen, Deutschland";

        VenueEditPanel panel = new VenueEditPanel(new VenueModel());

        wicketTester.startComponentInPage(panel);
        wicketTester.dumpPage();
        wicketTester.debugComponentTrees();
        // input name and validate field
        Component field = wicketTester.getComponentFromLastRenderedPage("form:address:input");
        wicketTester.newFormTester("form").setValue(field, searchAddress);
        wicketTester.clickLink("form:address:geocodeButton");
        wicketTester.dumpAjaxResponse();
        wicketTester.assertComponentOnAjaxResponse("form:geocodePanel");
        wicketTester.assertContains(resultAddress);
        wicketTester.assertContains(resultAddress2);
    }
}