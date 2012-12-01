package de.flower.rmt.ui.page.event.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.model.EventModel;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.event.EventCanceledPanel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import de.flower.rmt.ui.page.event.manager.edit.EventEditSecondaryPanel;
import de.flower.rmt.ui.page.event.manager.invitees.InviteeSecondaryPanel;
import de.flower.rmt.ui.page.event.manager.lineup.LineupSecondaryPanel;
import de.flower.rmt.ui.page.event.manager.teams.TeamsSecondaryPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class EventPage extends ManagerBasePage {

    private final static Logger log = LoggerFactory.getLogger(EventPage.class);

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
        Event event;
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
        // super(model); do not call super(model). model is set in init-method. overriding an already set model would
        // lead to detach of the model.
        init(model);
    }

    private void init(final IModel<Event> model) {
        setDefaultModel(model);
        setHeading("manager.event.edit.heading", null);
        final EventTabPanel tabPanel = new EventTabPanel(model) {
            @Override
            protected void onAjaxUpdate(final AjaxRequestTarget target, final int selectedTab) {
                // allow secondary panel to update when tabs change
                target.add(getSecondaryPanel());
            }
        };
        addMainPanel(tabPanel);

        getSecondaryPanel().add(new EventCanceledPanel(model));
        getSecondaryPanel().add(new EventDetailsPanel(model) {
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
        getSecondaryPanel().add(new EventEditSecondaryPanel(model) {
            @Override
            public boolean isVisible() {
                return tabPanel.getSelectedTab() == EventTabPanel.EVENT_EDIT_PANEL_INDEX;
            }
        });
        getSecondaryPanel().add(new LineupSecondaryPanel(model) {
            @Override
            public boolean isVisible() {
                return tabPanel.getSelectedTab() == EventTabPanel.LINEUP_PANEL_INDEX;
            }
        });
        getSecondaryPanel().add(new TeamsSecondaryPanel(model) {
            @Override
            public boolean isVisible() {
                return tabPanel.getSelectedTab() == EventTabPanel.TEAM_PANEL_INDEX;
            }
        });

        getSecondaryPanel().add(new AjaxEventListener(Event.class));
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
        return Pages.EVENTS.name();
    }

    @Override
    protected boolean hasModalWindow() {
        return true;
    }
}
