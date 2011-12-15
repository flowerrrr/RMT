package de.flower.rmt.ui.player.page.event;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.User;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResponseFormPanelTest extends AbstractWicketUnitTests {

    @Test
    public void testRender() {
        Invitee invitee = new Invitee(null, (User) null);
        wicketTester.startComponentInPage(new ResponseFormTestPanel(Model.of(invitee)));
        wicketTester.dumpComponentWithPage();
    }

    private static class ResponseFormTestPanel extends ResponseFormPanel {

        public ResponseFormTestPanel(final IModel<Invitee> model) {
            super(model);
        }

        @Override
        protected void onSubmit(final Invitee response, final AjaxRequestTarget target) {
        }
    }

}
