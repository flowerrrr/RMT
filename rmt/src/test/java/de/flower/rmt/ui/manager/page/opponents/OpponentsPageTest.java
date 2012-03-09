package de.flower.rmt.ui.manager.page.opponents;

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