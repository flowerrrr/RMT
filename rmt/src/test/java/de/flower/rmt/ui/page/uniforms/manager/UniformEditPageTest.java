package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.test.AbstractRMTWicketIntegrationTests;
import de.flower.rmt.ui.model.TeamModel;
import de.flower.rmt.ui.model.UniformModel;
import org.testng.annotations.Test;


public class UniformEditPageTest extends AbstractRMTWicketIntegrationTests {

    @Test
    public void testRender() {
        Team team = testData.createTeam("Bayern");
        final Uniform uniform = uniformManager.newInstance(team);
        wicketTester.startPage(new UniformEditPage(new UniformModel(uniform), new TeamModel(team)));
        wicketTester.dumpPage();
    }


}