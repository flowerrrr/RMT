package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AccountPageTest extends AbstractWicketIntegrationTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startPage(new AccountPage());
        wicketTester.dumpPage();
    }
}
