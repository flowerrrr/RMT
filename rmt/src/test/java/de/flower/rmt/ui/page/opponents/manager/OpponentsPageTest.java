package de.flower.rmt.ui.page.opponents.manager;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import org.testng.annotations.Test;
/**
 * 
 * @author flowerrrr
 */

public class OpponentsPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new OpponentsPage());
        wicketTester.dumpPage();
    }


}