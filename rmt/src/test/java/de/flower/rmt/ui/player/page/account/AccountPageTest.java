package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.test.AbstractWicketTests;

/**
 * @author flowerrrr
 */
public class AccountPageTest extends AbstractWicketTests {

    @Override
    public void testRender() {
        wicketTester.startPage(new AccountPage());
        wicketTester.dumpPage();
    }
}
