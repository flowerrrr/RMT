package de.flower.rmt.ui.player.page.event;

import de.flower.rmt.model.Response;
import de.flower.rmt.test.AbstractWicketTests;
import de.flower.rmt.ui.model.ResponseModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.testng.annotations.Test;

/**
 * @author flowerrrr
 */
public class ResponseFormPanelTest extends AbstractWicketTests {

    @Test
    public void testRender() {
        Response response = testData.createResponse();
        wicketTester.startComponentInPage(new ResponseFormTestPanel(new ResponseModel(response)));
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
