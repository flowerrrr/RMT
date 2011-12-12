package de.flower.rmt.ui.player.page.event;

import de.flower.common.test.wicket.AbstractWicketUnitTests;
import de.flower.rmt.model.Response;
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
        Response response = new Response(null, null);
        wicketTester.startComponentInPage(new ResponseFormTestPanel(Model.of(response)));
        wicketTester.dumpComponentWithPage();
    }

    private static class ResponseFormTestPanel extends ResponseFormPanel {

        public ResponseFormTestPanel(final IModel<Response> model) {
            super(model);
        }

        @Override
        protected void onSubmit(final Response response, final AjaxRequestTarget target) {
        }
    }

}
