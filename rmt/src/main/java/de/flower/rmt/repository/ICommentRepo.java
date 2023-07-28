package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;

import java.util.List;

/**
 * Uses Spring Data JPA library to define a XXXRepistory
 * interface that Spring will create a Bean object
 * with CRUD methods for a XXX.
 */
public interface ICommentRepo extends IRepository<Comment, Long> {

    List<Comment> findByInvitationAndAuthor(Invitation invitation, User author);
}
