package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.QInvitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.service.security.ISecurityService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventInvitationModel extends LoadableDetachableModel<Invitation> {

    @SpringBean
    private ISecurityService securityService;

    @SpringBean
    private IInvitationManager invitationManager;

    private IModel<Event> eventModel;

    public EventInvitationModel(IModel<Event> eventModel) {
        this.eventModel = eventModel;
        Injector.get().inject(this);
    }

    @Override
    protected Invitation load() {
        final Invitation invitation = invitationManager.findByEventAndUser(eventModel.getObject(), securityService.getUser(), QInvitation.invitation.event);
        return invitation;
    }
}
