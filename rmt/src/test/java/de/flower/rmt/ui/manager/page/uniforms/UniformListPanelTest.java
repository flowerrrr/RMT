package de.flower.rmt.ui.manager.page.uniforms;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Uniform;
import de.flower.rmt.service.IUniformManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author flowerrrr
 */
public class UniformListPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private IUniformManager uniformManager;

    @Test
    public void testRender() {
        Team team = testData.newTeamWithPlayers(20);
        List<Uniform> uniformList = testData.newUniformList(team);
        when(uniformManager.findAllByTeam(team)).thenReturn(uniformList);
        wicketTester.startComponentInPage(new UniformListPanel(Model.of(team)));
        wicketTester.dumpComponentWithPage();
    }

}
