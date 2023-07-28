package de.flower.rmt.ui.page.user.manager;

import de.flower.rmt.model.db.entity.Player_;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.PlayerManager;
import de.flower.rmt.test.AbstractRMTWicketMockitoTests;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.tester.FormTester;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;


public class PlayerTeamsPanelTest extends AbstractRMTWicketMockitoTests {

    @SpringBean
    private PlayerManager playerManager;

    @Override
    protected boolean isMockitoVerboseLogging() {
        return true;
    }

    @Test
    public void testRender() {
        final User user = testData.newUserWithTeams();
        user.setId(1L); // make it act persisted
        when(playerManager.findAllByUser(user, Player_.team)).thenReturn(user.getPlayers());
        when(playerManager.sortByTeam(user.getPlayers())).thenReturn(user.getPlayers());
        wicketTester.startComponentInPage(new PlayerTeamsPanel("panel", Model.of(user)));
        wicketTester.dumpComponentWithPage();

        verify(playerManager, times(1)).findAllByUser(user, Player_.team);

        wicketTester.assertVisible("list");
        wicketTester.assertInvisible("noTeam");

        // trigger onChange event
        FormTester formTester = wicketTester.newFormTester("0:form"); // select first form of list of forms.
        Component c = formTester.getForm().get("notification:input");
        formTester.setValue(c, new ResourceModel("choice.player.notification.false").getObject());
        wicketTester.executeAjaxEvent(c, "onchange");
        // verify does complain about springbean injected mocks. must get mock directly from appContext
        verify(playerManager, times(1)).save(user.getPlayers().get(0));
        wicketTester.dumpAjaxResponse();
    }

    @Test
    public void testNoTeam() {
        final User user = testData.newUser();
        wicketTester.startComponentInPage(new PlayerTeamsPanel("panel", Model.of(user)));
        wicketTester.dumpComponentWithPage();

        verify(playerManager, never()).findAllByUser(user);

        wicketTester.assertVisible("noTeam");
        wicketTester.assertInvisible("list");
    }

}
