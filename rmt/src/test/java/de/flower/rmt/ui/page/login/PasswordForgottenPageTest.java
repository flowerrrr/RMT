package de.flower.rmt.ui.page.login;

import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class PasswordForgottenPageTest extends AbstractRMTWicketMockitoTests {

    @Test
    public void testRender() {
        wicketTester.startPage(PasswordForgottenPage.class);
        wicketTester.dumpPage();
    }

}
