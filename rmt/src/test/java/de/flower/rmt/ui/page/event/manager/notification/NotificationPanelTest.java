package de.flower.rmt.ui.page.event.manager.notification;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.EventModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class NotificationPanelTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Event event = testData.createEvent();
        wicketTester.startComponentInPage(new NotificationPanel("panel", new EventModel(event)));
        wicketTester.dumpPage();

        wicketTester.debugComponentTrees();
        // select template
        wicketTester.clickLink("panel:form:body:selectTemplatePanel:list:0:link");
    }
}
