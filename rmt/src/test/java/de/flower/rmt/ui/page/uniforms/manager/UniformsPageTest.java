package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.TeamModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */

public class UniformsPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        wicketTester.startPage(new UniformsPage(new TeamModel(testData.createTeam("foo"))));
        wicketTester.dumpPage();
    }
}