package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.util.Check;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Invitation_;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.IPlayerManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
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
// TODO (flowerrrr - 23.10.11) unite with managers InvitationListPanel
public class InvitationListPanel extends BasePanel {

    @SpringBean
    private IInvitationManager invitationManager;

    @SpringBean
    private IPlayerManager playerManager;

    public InvitationListPanel(IModel<Event> model) {
        Check.notNull(model);
        add(createListView("acceptedList", RSVPStatus.ACCEPTED, model, true));
        add(createListView("unsureList", RSVPStatus.UNSURE, model, false));
        add(createListView("declinedList", RSVPStatus.DECLINED, model, false));
        add(createListView("noresponseList", RSVPStatus.NORESPONSE, model, false));
        add(new AjaxEventListener(Invitation.class));
    }

    private Component createListView(String id, RSVPStatus status, IModel<Event> model, final boolean printOrder) {
        ListView list = new EntityListView<Invitation>(id, getInvitationList(model, status)) {
            @Override
            protected void populateItem(ListItem<Invitation> item) {
                item.add(createInvitationFragement(item, printOrder));
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

    private Component createInvitationFragement(final ListItem<Invitation> item, final boolean printOrder) {
        final Invitation invitation = item.getModelObject();
        Fragment frag = new Fragment("itemPanel", "itemFragment", this);
        final Label label = new Label("position", "#" + (item.getIndex() + 1));
        label.setVisible(printOrder);
        frag.add(label);
        frag.add(new Label("name", invitation.getName()));
        frag.add(new Label("comment", invitation.getComment()).setVisible(!StringUtils.isBlank(invitation.getComment())));
        frag.add(new Label("managerComment", invitation.getManagerComment()).setVisible(!StringUtils.isBlank(invitation.getManagerComment())));
        ExternalLink link = Links.mailLink("emailLink", (invitation.hasEmail() ? invitation.getEmail() : ""), null);
        link.setVisible(invitation.hasEmail());
        frag.add(link);
        return frag;
    }

    private IModel<List<Invitation>> getInvitationList(final IModel<Event> model, final RSVPStatus rsvpStatus) {
        return new LoadableDetachableModel<List<Invitation>>() {
            @Override
            protected List<Invitation> load() {
                return invitationManager.findAllByEventAndStatus(model.getObject(), rsvpStatus, Invitation_.user);
            }
        };
    }
}