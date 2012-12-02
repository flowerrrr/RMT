package de.flower.rmt.ui.page.event.manager.teams;

import de.flower.common.ui.ajax.dragndrop.DraggableDto;
import de.flower.common.ui.ajax.dragndrop.DroppableBehavior;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.QEventTeamPlayer;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.DraggableEntityLabel;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.EntityLabel;
import de.flower.rmt.ui.page.event.manager.teams.TeamsSecondaryPanel.EventTeamInviteeListPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventTeamPanel extends BasePanel<EventTeam> {

    @SpringBean
    private IEventTeamManager eventTeamManager;

    public EventTeamPanel(final IModel<EventTeam> model) {
        super(model);

        final WebMarkupContainer grid = new WebMarkupContainer("grid");
        add(grid);

        final WebMarkupContainer itemContainer = new WebMarkupContainer("itemContainer");
        grid.add(itemContainer);
        itemContainer.add(new AjaxEventListener(model, EventTeam.class));

        // render existing player items
        ListView<EventTeamPlayer> items = new ListView<EventTeamPlayer>("items", getListModel(model)) {
            @Override
            protected void populateItem(final ListItem<EventTeamPlayer> item) {
                Invitation invitation = item.getModelObject().getInvitation();
                EntityLabel entityLabel = new DraggableEntityLabel(invitation.getId(), invitation.getName(), true) {
                    @Override
                    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {
                        // remove player
                        eventTeamManager.removeInvitation(invitationId);
                        // to avoid updating all EventTeamPanels on the page use the this object as event type.
                        AjaxEventSender.send(this, model);
                        AjaxEventSender.send(this, EventTeamInviteeListPanel.class);
                    }
                };
                item.add(entityLabel);
            }
        };
        itemContainer.add(items);

        grid.add(new DroppableBehavior(true) {
            @Override
            protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                eventTeamManager.addPlayer(model.getObject().getId(), dto.entityId);
                AjaxEventSender.send(grid, model);
                // must update other teams if player was moved from one team to another
                AjaxEventSender.entityEvent(grid, EventTeamPlayer.class);
            }
        });
    }

    private IModel<List<EventTeamPlayer>> getListModel(final IModel<EventTeam> model) {
        return new LoadableDetachableModel<List<EventTeamPlayer>>() {
            @Override
            protected List<EventTeamPlayer> load() {
                return eventTeamManager.findEventTeamPlayers(model.getObject(), QEventTeamPlayer.eventTeamPlayer.invitation);
            }
        };
    }
}
