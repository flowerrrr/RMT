package de.flower.rmt.ui.page.event.player;

import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.QInvitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.service.CommentManager;
import de.flower.rmt.service.InvitationManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventInvitationModel extends LoadableDetachableModel<Invitation> {

    @SpringBean
    private SecurityService securityService;

    @SpringBean
    private InvitationManager invitationManager;

    @SpringBean
    private CommentManager commentManager;

    private IModel<Event> eventModel;

    public EventInvitationModel(IModel<Event> eventModel) {
        this.eventModel = eventModel;
        Injector.get().inject(this);
    }

    @Override
    protected Invitation load() {
        final Invitation invitation = invitationManager.findByEventAndUser(eventModel.getObject(), securityService.getUser(), QInvitation.invitation.event, QInvitation.invitation.comments);
        // init comment with main comment of current user (either the player or manager)
        Comment comment = commentManager.findByInvitationAndAuthor(invitation, securityService.getUser(), 0);
        if (comment != null) {
            invitation.setComment(comment.getText());
        }
        return invitation;
    }
}
