package de.flower.rmt.ui.page.event.manager;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.Event_;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.feedback.NoInvitationSentMessage;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import de.flower.rmt.ui.page.event.EventSelectPanel;
import de.flower.rmt.ui.page.event.manager.invitees.InviteeSecondaryPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventPage extends ManagerBasePage {

    public final static String PARAM_EVENTID = "event";

    @SpringBean
    private IEventManager eventManager;

    public static PageParameters getPageParams(Long eventId) {
        return new PageParameters().set(EventPage.PARAM_EVENTID, eventId);
    }

    public static PageParameters getPageParams(Long eventId, int tabIndex) {
        return getPageParams(eventId).set(EventTabPanel.TAB_INDEX_KEY, tabIndex);
    }

    public EventPage(PageParameters params) {
        Event event = null;
        try {
            Long eventId = params.get(EventPage.PARAM_EVENTID).toLong();
            event = eventManager.loadById(eventId);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new AbortWithHttpErrorCodeException(404, "Invalid page parameter: " + e.getMessage());
        }
        init(new EventModel(event));
    }

    public EventPage(IModel<Event> model) {
        super(model);
        init(model);
    }

    private void init(IModel<Event> model) {
        setDefaultModel(model);
        setHeading("manager.event.edit.heading", null);
        final EventTabPanel tabPanel;
        addMainPanel(tabPanel = new EventTabPanel(model) {
            @Override
            protected void onAjaxUpdate(final AjaxRequestTarget target, final int selectedTab) {
                // allow secondary panel to update when tabs change
                target.add(getSecondaryPanel());
            }
        });
        addSecondaryPanel(new EventSelectPanel(model, getListModel()) {
            @Override
            protected void onClick(final IModel<Event> model) {
                setResponsePage(EventPage.class, EventPage.getPageParams(model.getObject().getId(), tabPanel.getSelectedTab()));
            }
        });

        getSecondaryPanel().add(new EventDetailsPanel(model, View.MANAGER) {
            @Override
            public boolean isVisible() {
                return tabPanel.getSelectedTab() == EventTabPanel.INVITATIONS_PANEL_INDEX
                        || tabPanel.getSelectedTab() == EventTabPanel.NOTIFICATION_PANEL_INDEX;
            }
        });
        getSecondaryPanel().add(new InviteeSecondaryPanel(model) {
            @Override
            public boolean isVisible() {
                return tabPanel.getSelectedTab() == EventTabPanel.INVITEES_PANEL_INDEX;
            }
        });
    }

    private IModel<List<Event>> getListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAll(Event_.team);
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
