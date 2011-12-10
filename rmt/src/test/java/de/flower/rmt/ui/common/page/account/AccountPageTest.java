package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.test.AbstractWicketTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class AccountPageTest extends AbstractWicketTests {

    @Override
    @Test
    public void testRender() {
        wicketTester.startPage(new AccountPage());
        wicketTester.dumpPage();
    }
}
