package de.flower.rmt.ui.page.squad.manager;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.service.PlayerManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;


public class PlayerListPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private PlayerManager playerManager;

    @Test
    public void testRender() {
        Team team = testData.newTeamWithPlayers(20);
        when(playerManager.findAllByTeam(team)).thenReturn(team.getPlayers());
        wicketTester.startComponentInPage(new PlayerListPanel(Model.of(team)));
        wicketTester.dumpComponentWithPage();
    }

}
