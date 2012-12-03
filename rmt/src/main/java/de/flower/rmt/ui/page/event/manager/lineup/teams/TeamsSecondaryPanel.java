package de.flower.rmt.ui.page.event.manager.lineup.teams;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.DraggableInviteeListPanel;
import de.flower.rmt.ui.page.event.manager.lineup.match.LineupSecondaryPanel.LineupPublishPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class TeamsSecondaryPanel extends BasePanel {

    public TeamsSecondaryPanel(final IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new LineupPublishPanel(model));

        add(new EventTeamInviteeListPanel(model));
    }

    public static class EventTeamInviteeListPanel extends DraggableInviteeListPanel {

        @SpringBean
        private IEventTeamManager eventTeamManager;

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
            return playerItemListModel.getObject().contains(invitation);
        }

        @Override
        protected void onDetach() {
            super.onDetach();
            if (playerItemListModel != null) {
                playerItemListModel.detach();
            }
        }
    }}
