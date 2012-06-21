package de.flower.rmt.service;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CommentManager extends AbstractService implements ICommentManager {

    @Autowired
    private ICommentRepo commentRepo;

    @Autowired
    private IActivityManager activityManager;

    @Override
    public Comment newInstance(final Invitation invitation) {
        Comment comment = new Comment(invitation, securityService.getUser());
        return comment;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(final Comment comment) {
        validate(comment);

        Comment origComment = null;
        if (!comment.isNew()) {
            origComment = commentRepo.findOne(comment.getId());
        }
        activityManager.onCommentUpdated(comment, origComment); // must be called before saving comment. otherwise origComment would be overriden with new values.
        commentRepo.save(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComment(Invitation invitation, String text, User author) {
        Check.isTrue(!invitation.isNew());

        // find existing comment
        Comment comment = findByInvitationAndAuthor(invitation, author, 0);
        if (comment != null) {
            comment.setText(text);
        } else {
            // no comment for author found -> create new one
            comment = new Comment(text, invitation, author);
        }
        commentRepo.save(comment);
        // activityManager.onCommentUpdated(comment); // activity is logged in InvitationManager
    }

    @Override
    public Comment findByInvitationAndAuthor(final Invitation invitation, final User author, final int index) {
        List<Comment> authorComments = commentRepo.findByInvitationAndAuthor(invitation, author);
        if (authorComments.size() <= index) {
            return null;
        } else {
            return authorComments.get(index);
        }
    }

    @Override
    public void remove(final Comment comment) {
        commentRepo.delete(comment.getId());
    }
}
