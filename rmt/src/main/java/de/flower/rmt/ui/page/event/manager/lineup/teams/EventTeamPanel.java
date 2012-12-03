package de.flower.rmt.ui.page.event.manager.lineup.teams;

import com.google.common.collect.Lists;
import de.flower.common.ui.ajax.dragndrop.DraggableDto;
import de.flower.common.ui.ajax.dragndrop.DroppableBehavior;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.QEventTeamPlayer;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.DraggableEntityLabel;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.EntityLabel;
import de.flower.rmt.ui.page.event.manager.lineup.teams.TeamsSecondaryPanel.EventTeamInviteeListPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventTeamPanel extends RMTBasePanel<EventTeam> {

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
                EntityLabel entityLabel = new DraggableEntityLabel(invitation.getId(), invitation.getName(), EventTeamPanel.this.isManagerView()) {
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
                if (EventTeamPanel.this.isManagerView()) {
                    item.add(new DroppableBehavior(true) {
                        @Override
                        protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                            eventTeamManager.addPlayer(model.getObject().getId(), dto.entityId, item.getModelObject().getId());
                            AjaxEventSender.send(grid, model);
                            // must update other teams if player was moved from one team to another
                            AjaxEventSender.entityEvent(grid, EventTeam.class);
                        }
                    });
                }
            }
        };
        itemContainer.add(items);

        // render placerholders as droppable containers for appending items to end of list
        ListView<Object> placeholders = new ListView<Object>("placeholders", getPlaceholdersModel(items.getModel())) {
            @Override
            protected void populateItem(final ListItem<Object> item) {
                item.add(new DroppableBehavior(true) {
                    @Override
                    protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                        eventTeamManager.addPlayer(model.getObject().getId(), dto.entityId, null);
                        AjaxEventSender.send(grid, model);
                        // must update other teams if player was moved from one team to another
                        AjaxEventSender.entityEvent(grid, EventTeam.class);
                    }
                });
            }
        };
        placeholders.setVisible(isManagerView());
        itemContainer.add(placeholders);
    }

    private IModel<? extends List<?>> getPlaceholdersModel(final IModel<? extends List<EventTeamPlayer>> model) {
        return new AbstractReadOnlyModel<List<?>>() {
            @Override
            public List<?> getObject() {
                // return list so that at least one placeholder and combined size of players and placeholders is minimum 3.
                int size = Math.max(1, 3 - model.getObject().size());
                return newList(size, null);
            }
        };
    }

    private static <T> List<T> newList(int size, T defaultElement) {
        List<T> list = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            list.add(defaultElement);
        }
        return list;
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
