package de.flower.rmt.ui.manager.page.invitations;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.page.event.EventDetailsPanel;
import de.flower.rmt.ui.common.page.event.EventPagerPanel;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class InvitationsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public InvitationsPage(IModel<Event> model) {
        super(model);
        setHeading("manager.invitations.heading", null);

        final InvitationListPanel invitationListPanel = new InvitationListPanel(model);
        addMainPanel(invitationListPanel);
        addSecondaryPanel(createEventPagerPanel(model), new EventDetailsPanel(model));
    }

    private EventPagerPanel createEventPagerPanel(final IModel<Event> model) {
        return new EventPagerPanel(model, getListModel()) {

            @Override
            protected void onClick(final IModel<Event> model) {
                setResponsePage(new InvitationsPage(model));
            }
        };
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll();
            }
        };
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        // makes messages back-button and reload-save. must be called after super.onBeforeRender to have this
        // message listed after messages of super-page
        // check if an invitation has been sent for this event already
        info(new NoInvitationSentMessage((IModel<Event>) getDefaultModel()));
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
