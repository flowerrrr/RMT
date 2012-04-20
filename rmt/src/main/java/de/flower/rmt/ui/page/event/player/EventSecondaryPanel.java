package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.util.Collections;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.User;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.model.InvitationModel;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.event.EventDetailsPanel;
import de.flower.rmt.ui.page.event.EventSelectPanel;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author flowerrrr
 */
public class EventSecondaryPanel extends BasePanel {

    @SpringBean
    private IEventManager eventManager;

    @SpringBean
    private IInvitationManager invitationManager;

    public EventSecondaryPanel(IModel<Event> model) {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(createEventSelectPanel(model));

        final IModel<Invitation> invitationModel = getInvitationModel(model);
        InvitationFormPanel invitationFormPanel = new InvitationFormPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, invitationModel) {

            @Override
            protected void onSubmit(final Invitation invitation, final AjaxRequestTarget target) {
                // save invitation and update invitationlistpanel
                invitationManager.save(invitation);
                AjaxEventSender.entityEvent(this, Invitation.class);
            }
        };
        // make form visible if user hasn't responded yet
        invitationFormPanel.setVisible(invitationModel.getObject() != null && invitationModel.getObject().getStatus() == RSVPStatus.NORESPONSE);

        add(new AjaxSlideTogglePanel("invitationFormPanel", "player.event.invitationform.heading", invitationFormPanel) {
            @Override
            public boolean isVisible() {
                // completely hide panel if user is not invitee of this event.
                return invitationModel.getObject() != null;
            }
        });

        add(new EventDetailsPanel(model, View.PLAYER));

        add(Links.mailLink("allMailLink", getAllEmailAddresses(model.getObject()), null));

        add(Links.mailLink("managerMailLink", getManagerEmailAddress(model.getObject()), null));
    }

    private Panel createEventSelectPanel(final IModel<Event> model) {
        return new EventSelectPanel(model, getUpcomingEventList()) {

            @Override
            protected void onClick(final IModel<Event> model) {
                setResponsePage(new EventPage(model));
            }
        };
    }

    private IModel<Invitation> getInvitationModel(final IModel<Event> model) {
        final Invitation invitation = invitationManager.findByEventAndUser(model.getObject(), getUserDetails().getUser());
        if (invitation != null) {
            return new InvitationModel(invitation);
        } else {
            return new Model();
        }
    }

    private IModel<List<Event>> getUpcomingEventList() {
        final IModel<User> userModel = new UserModel(getUserDetails().getUser());
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findAllUpcomingByUser(userModel.getObject());
            }
        };
    }

    private String getAllEmailAddresses(final Event event) {
        List<InternetAddress[]> list = invitationManager.findAllForNotificationByEventSortedByName2(event);
        // convert to list of email addresses
        List<String> stringList = Collections.convert(Collections.flattenArray(list), new Collections.IElementConverter<InternetAddress, String>() {
            @Override
            public String convert(final InternetAddress ia) {
                return ia.toString();
            }
        });
        // outlook likes ';', iphone mail client prefers ','. but according to most sources ';' is correct when used in mailto.
        // TODO (flowerrrr - 14.04.12) could try to detect user agent
        return StringUtils.join(stringList, "; ");
    }

    private String getManagerEmailAddress(final Event event) {
        return event.getCreatedBy().getEmail();
    }
}
