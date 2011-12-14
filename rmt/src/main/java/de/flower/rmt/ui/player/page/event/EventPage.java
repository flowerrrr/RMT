package de.flower.rmt.ui.player.page.event;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IResponseManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.model.ResponseModel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    public final static String PARAM_EVENTID = "event";

    @SpringBean
    private IResponseManager responseManager;

    @SpringBean
    private IEventManager eventManager;

    /**
     * Deep link support
     *
     * @param params
     */
    public EventPage(PageParameters params) {
        // TODO (flowerrrr - 14.12.11) handle invalid parameter and redirect to some error page (eventnotfound)
        Long eventId = params.get(PARAM_EVENTID).toLong();
        Event event = eventManager.loadByIdAndUser(eventId, getUserDetails().getUser());
        init(new EventModel(event));
    }

    public EventPage(final IModel<Event> model) {
        super(model);
        init(model);
    }

    private void init(IModel<Event> model) {
        setDefaultModel(model);
        addHeading("player.event.heading");

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

        addMainPanel(responseListPanel);
        addSecondaryPanel(new EventDetailsPanel(model), responseFormPanel);
    }

    private IModel<Response> getResponseModel(final IModel<Event> model) {
        final Response response = responseManager.findByEventAndUser(model.getObject(), getUserDetails().getUser());
        if (response == null) {
            return new ResponseModel(model);
        } else {
            return new ResponseModel(response);
        }
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }
}
