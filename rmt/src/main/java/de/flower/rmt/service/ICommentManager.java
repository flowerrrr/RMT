package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;

/**
 * @author flowerrrr
 */
public interface ICommentManager {

    Comment newInstance(Invitation invitation);

    void save(Comment comment);

    /**
     * Updates the "first" or "main" comment of a user.
     */
    void updateOrRemoveComment(Invitation invitation, String comment, User author);

    Comment findByInvitationAndAuthor(Invitation invitation, User user, int index);

    void remove(Comment comment);

}
