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

    void updateComment(Invitation invitation, String comment);

    Comment findByInvitationAndAuthor(Invitation invitation, User user, int index);

    void remove(Comment comment);
}
