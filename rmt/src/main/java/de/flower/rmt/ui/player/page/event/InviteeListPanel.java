package de.flower.rmt.ui.player.page.event;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitee;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInviteeManager;
import de.flower.rmt.service.IPlayerManager;
import de.flower.rmt.ui.common.panel.BasePanel;
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
 * @author flowerrrr
 */
// TODO (flowerrrr - 23.10.11) unite with managers InviteeListPanel
public class InviteeListPanel extends BasePanel {

    @SpringBean
    private IInviteeManager inviteeManager;

    @SpringBean
    private IPlayerManager playerManager;

    public InviteeListPanel(IModel<Event> model) {
        Check.notNull(model);
        add(createListView("acceptedList", RSVPStatus.ACCEPTED, model, true));
        add(createListView("unsureList", RSVPStatus.UNSURE, model, false));
        add(createListView("declinedList", RSVPStatus.DECLINED, model, false));
        add(createListView("noresponseList", RSVPStatus.NORESPONSE, model, false));
    }

    private Component createListView(String id, RSVPStatus status, IModel<Event> model, final boolean printOrder) {
        ListView list = new ListView<Invitee>(id, getInviteeList(model, status)) {
            @Override
            protected void populateItem(ListItem<Invitee> item) {
                item.add(createInviteeFragement(item, printOrder));
            }

            /**
             * Needed for wicket:enclosure to work.
             * @return
             */
            @Override
            public boolean isVisible() {
                return !this.getList().isEmpty();
            }
        };
        return list;
    }

    private Component createInviteeFragement(final ListItem<Invitee> item, final boolean printOrder) {
        final Invitee invitee = item.getModelObject();
        Fragment frag = new Fragment("itemPanel", "itemFragment", this);
        final Label label = new Label("position", "#" + (item.getIndex() + 1));
        label.setVisible(printOrder);
        frag.add(label);
        frag.add(new Label("name", invitee.getName()));
        frag.add(new Label("comment", invitee.getComment()));
        return frag;
    }

    private IModel<List<Invitee>> getInviteeList(final IModel<Event> model, final RSVPStatus rsvpStatus) {
        return new LoadableDetachableModel<List<Invitee>>() {
            @Override
            protected List<Invitee> load() {
                return inviteeManager.findByEventAndStatus(model.getObject(), rsvpStatus);
            }
        };
    }

}
