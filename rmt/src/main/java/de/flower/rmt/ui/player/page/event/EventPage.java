package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IResponseManager;
import de.flower.rmt.ui.model.ResponseModel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    @SpringBean
    private IResponseManager responseManager;

    public EventPage(final IModel<Event> model) {
        super(model);
        addHeading("player.event.heading");

        addMainPanel(new EventDetailsPanel(model));

        ResponseFormPanel responseFormPanel = new ResponseFormPanel(getResponseModel(model)) {

            @Override
            protected void onSubmit(final Response response, final AjaxRequestTarget target) {
                // save response and update responselistpanel
                responseManager.save(response);
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityAll(Response.class)));
            }
        };

        final ResponseListPanel responseListPanel = new ResponseListPanel(model);
        responseListPanel.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Response.class)));

        addSecondaryPanel(responseFormPanel, responseListPanel);
    }

    private IModel<Response> getResponseModel(final IModel<Event> model) {
        final Response response = responseManager.findByEventAndUser(model.getObject(), getUser());
        if (response == null) {
            return new ResponseModel(model.getObject());
        } else {
            return new ResponseModel(response);
        }
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }
}
