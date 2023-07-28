package de.flower.rmt.ui.page.event.manager.lineup.teams;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.EventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.DraggableInviteeListPanel;
import de.flower.rmt.ui.page.event.manager.lineup.match.LineupSecondaryPanel.LineupPublishPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class TeamsSecondaryPanel extends RMTBasePanel<Event> {

    @SpringBean
    private EventTeamManager eventTeamManager;

    public TeamsSecondaryPanel(final IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new LineupPublishPanel(model));

        AjaxLink button = new AjaxLink("addTeamButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                eventTeamManager.addTeam(model.getObject());
                AjaxEventSender.entityEvent(this, EventTeam.class);
            }
        };
        button.setVisible(isManagerView());
        add(button);

        add(new EventTeamInviteeListPanel(model));
    }

    public static class EventTeamInviteeListPanel extends DraggableInviteeListPanel {

        @SpringBean
        private EventTeamManager eventTeamManager;

        // used to filter out those players that are already dragged to the player-grids.
        private IModel<List<Invitation>> playerItemListModel;

        public EventTeamInviteeListPanel(final IModel<Event> model) {
            super(model);
            add(new AjaxEventListener(EventTeamPlayer.class, EventTeamInviteeListPanel.class));

            playerItemListModel = new LoadableDetachableModel<List<Invitation>>() {
                @Override
                protected List<Invitation> load() {
                    List<Invitation> lineupItems = eventTeamManager.findInvitationsInEventTeams(model.getObject());
                    return lineupItems;
                }
            };
        }

        @Override
        protected boolean isDraggablePlayerVisible(final Invitation invitation) {
            return !playerItemListModel.getObject().contains(invitation);
        }

        @Override
        public void detachModels() {
            super.detachModels();
            if (playerItemListModel != null) {
                playerItemListModel.detach();
            }
        }
    }
}
