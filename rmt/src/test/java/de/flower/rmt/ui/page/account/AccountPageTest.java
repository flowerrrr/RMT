package de.flower.rmt.ui.page.account;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AccountPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new AccountPage());
        wicketTester.dumpPage();
    }
}
