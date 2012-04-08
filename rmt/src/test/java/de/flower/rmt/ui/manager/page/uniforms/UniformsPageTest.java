package de.flower.rmt.ui.manager.page.uniforms;

import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.TeamModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class UniformsPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new UniformsPage(new TeamModel(testData.createTeam("foo"))));
        wicketTester.dumpPage();
    }
}