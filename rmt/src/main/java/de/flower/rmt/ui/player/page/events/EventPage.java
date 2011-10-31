package de.flower.rmt.ui.player.page.events;

import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.ajax.updatebehavior.events.ShowResponseFormEvent;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IResponseManager;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventPage extends PlayerBasePage {

    private AjaxSlideTogglePanel responseFormPanelContainer;

    public EventPage(final IModel<Event> model) {
        super(model);
        add(new EventDetailsPanel("eventDetailsPanel", model));

        Panel responseFormPanel = new ResponseFormPanel("responseFormPanel") {

            @SpringBean
            private IResponseManager responseManager;

            @Override
            protected void onSubmit(final ResponseFormPanel.FormResponse formResponse, final AjaxRequestTarget target) {
                // save response and update responselistpanel
                responseManager.respond(model.getObject(), formResponse.getStatus(), formResponse.getComment());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityAll(Response.class)));
            }

            @Override
            protected void onClose(final AjaxRequestTarget target) {
                responseFormPanelContainer.hide(target);
            }
        };

        responseFormPanelContainer = new AjaxSlideTogglePanel("responseFormPanelContainer", responseFormPanel);
        // add behavior that slides open the response form if a link in the response list is clicked.
        // implementation was chosen to show how decoupling of components work.
        responseFormPanelContainer.add(new AjaxUpdateBehavior(new ShowResponseFormEvent()) {
            @Override
            public void addTarget(final AjaxRequestTarget target, final Component component) {
                responseFormPanelContainer.show(target);
            }
        });
        add(responseFormPanelContainer);

        final ResponseListPanel responseListPanel = new ResponseListPanel("responseListPanel", model);
        responseListPanel.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Response.class)));
        add(responseListPanel);
    }
}
