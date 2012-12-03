package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.markup.html.tab.AbstractAjaxTabbedPanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.EventType;
import de.flower.rmt.ui.page.event.manager.lineup.teams.TeamsEditPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventTabPanel extends AbstractAjaxTabbedPanel<Event> {

    public static final int INVITATIONS_PANEL_INDEX = 0;

    public static final int LINEUP_PANEL_INDEX = 1;

    public EventTabPanel(IModel<Event> model) {
        super(model);
    }

    @Override
    protected void addTabs(final List<ITab> tabs) {
        final IModel<Event> model = this.getModel();
        tabs.add(new AbstractTab(new ResourceModel("manager.event.tab.invitations")) {
            @Override
            public Panel getPanel(String panelId) {
                return new InvitationListPanel(panelId, model);
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("manager.event.tab.lineup")) {
            @Override
            public Panel getPanel(String panelId) {
                return new LineupPanel(panelId, model);
            }

            @Override
            public boolean isVisible() {
                return model.getObject().getEventType() != EventType.Event  && model.getObject().getEventType() != EventType.Training;
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("manager.event.tab.lineup")) {
            @Override
            public Panel getPanel(String panelId) {
                return new TeamsEditPanel(panelId, model);
            }

            @Override
            public boolean isVisible() {
                return model.getObject().getEventType() == EventType.Training;
            }
        });
    }
}
