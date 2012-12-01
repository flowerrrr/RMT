package de.flower.rmt.ui.page.event.manager.teams;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.EventTeamPlayer;
import de.flower.rmt.model.db.entity.QEventTeamPlayer;
import de.flower.rmt.model.dto.InvitationDto;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.LineupEditPanel.DropCallbackBehavior;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
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
public class EventTeamPanel extends BasePanel<EventTeam> {

    @SpringBean
    private IEventTeamManager eventTeamManager;

    public EventTeamPanel(final IModel<EventTeam> model) {
        super(model);

        final WebMarkupContainer grid = new WebMarkupContainer("grid");
        add(grid);

        final WebMarkupContainer itemContainer = new WebMarkupContainer("itemContainer");
        itemContainer.add(new AjaxEventListener(model));
        grid.add(itemContainer);

        // render existing player items
        ListView<EventTeamPlayer> items = new ListView<EventTeamPlayer>("items", getListModel(model)) {
            @Override
            protected void populateItem(final ListItem<EventTeamPlayer> item) {
                EventTeamPlayer eventTeamPlayer = item.getModelObject();

                PlayerPanel playerPanel = new PlayerPanel(eventTeamPlayer.getInvitation(), true) {
                    @Override
                    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {
                        // remove player
                        eventTeamManager.removeInvitation(invitationId);
                        // to avoid updating all EventTeamPanels on the page use the this object as event type.
                        AjaxEventSender.send(this, model);
                    }
                };
                item.add(playerPanel);
            }
        };
        itemContainer.add(items);

        final AbstractDefaultAjaxBehavior behavior = new DropCallbackBehavior() {
            @Override
            protected void onDrop(final AjaxRequestTarget target, final InvitationDto dto) {
                System.out.println("onDrop");
                eventTeamManager.addPlayer(model.getObject().getId(), dto.invitationId);
                AjaxEventSender.send(getComponent(), model);
            }
        };
        add(behavior);
        grid.add(AttributeModifier.replace("url", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "'" + behavior.getCallbackUrl().toString() + "'";
            }
        }));
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
