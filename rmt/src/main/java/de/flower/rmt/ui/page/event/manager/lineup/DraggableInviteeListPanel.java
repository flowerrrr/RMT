package de.flower.rmt.ui.page.event.manager.lineup;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Invitation_;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.DraggableEntityLabel;
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
public abstract class DraggableInviteeListPanel extends BasePanel {

    @SpringBean
    private InvitationManager invitationManager;

    public DraggableInviteeListPanel(final IModel<Event> model) {

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

        DraggableEntityLabel draggablePlayer = new DraggableEntityLabel(invitation.getId(), invitation.getName(), false);
        draggablePlayer.setVisible(isDraggablePlayerVisible(invitation));
        frag.add(draggablePlayer);

        return frag;
    }

    private IModel<List<Invitation>> getInvitationList(final IModel<Event> model, final RSVPStatus status) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                List<Invitation> invitations = invitationManager.findAllByEventAndStatusSortedByName(model.getObject(), status, Invitation_.user);
                return Lists.newArrayList(Collections2.filter(invitations, new Predicate<Invitation>() {
                    @Override
                    public boolean apply(final Invitation input) {
                        return isDraggablePlayerVisible(input);
                    }
                }));
            }
        };
    }

    protected abstract boolean isDraggablePlayerVisible(Invitation invitation);

}
