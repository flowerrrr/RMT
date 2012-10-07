package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.ILineupManager;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Lists all invitees as drag-able labels.
 *
 * @author flowerrrr
 */
public class LineupInviteeListPanel extends BasePanel {

    @SpringBean
    private IInvitationManager invitationManager;

    @SpringBean
    private ILineupManager lineupManager;

    // used to filter out those players that are already dragged to the lineup-grid.
    private IModel<List<LineupItem>> lineupItemListModel;

    public LineupInviteeListPanel(final IModel<Event> model) {

        add(new AjaxEventListener(LineupItem.class));

        lineupItemListModel = new LoadableDetachableModel<List<LineupItem>>() {
            @Override
            protected List<LineupItem> load() {
                List<LineupItem> lineupItems = lineupManager.findLineupItems(model.getObject(), QLineupItem.lineupItem.invitation);
                return lineupItems;
            }
        };

        add(createListView("acceptedList", RSVPStatus.ACCEPTED, model));
        add(createListView("unsureList", RSVPStatus.UNSURE, model));
        add(createListView("declinedList", RSVPStatus.DECLINED, model));
        add(createListView("noresponseList", RSVPStatus.NORESPONSE, model));
    }

    private Component createListView(String id, RSVPStatus status, IModel<Event> model) {
        final IModel<List<Invitation>> listModel = getInvitationList(model, status);
        ListView<Invitation> list = new ListView<Invitation>(id, listModel) {
            @Override
            protected void populateItem(final ListItem<Invitation> item) {
                item.add(createInvitationFragement(item));
            }
        };
        return list;
    }

    private Component createInvitationFragement(ListItem<Invitation> item) {
        final Invitation invitation = item.getModelObject();
        Fragment frag = new Fragment("itemPanel", "itemFragment", this);

        frag.add(new Label("placeholder", invitation.getName()));

        DraggablePlayerPanel draggablePlayer = new DraggablePlayerPanel(item.getModelObject(), null, false);
        draggablePlayer.setVisible(!isInLineup(invitation));
        frag.add(draggablePlayer);

        return frag;
    }

    private IModel<List<Invitation>> getInvitationList(final IModel<Event> model, final RSVPStatus status) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                List<Invitation> invitations = invitationManager.findAllByEventAndStatusSortedByName(model.getObject(), status, Invitation_.user);
                return invitations;
            }
        };
    }

    private boolean isInLineup(Invitation invitation) {
        for (LineupItem item : lineupItemListModel.getObject()) {
            if (item.getInvitation().equals(invitation)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (lineupItemListModel != null) {
            lineupItemListModel.detach();
        }
    }
}
