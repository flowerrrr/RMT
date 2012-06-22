package de.flower.rmt.ui.page.calendar;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class CalendarPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        User user = testData.createUser();
        wicketTester.startPage(new CalendarPage(new UserModel(user)));
        wicketTester.dumpPage();
        wicketTester.dumpComponentWithPage();
    }
}