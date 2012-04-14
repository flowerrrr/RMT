package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Uniform;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.TeamModel;
import de.flower.rmt.ui.model.UniformModel;
import org.testng.annotations.Test;

/**
 * 
 * @author flowerrrr
 */

public class UniformEditPageTest extends AbstractWicketIntegrationTests {

    @Test
    public void testRender() {
        Team team = testData.createTeam("Bayern");
        final Uniform uniform = uniformManager.newInstance(team);
        wicketTester.startPage(new UniformEditPage(new UniformModel(uniform), new TeamModel(team)));
        wicketTester.dumpPage();
    }


}