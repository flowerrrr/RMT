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

    @Override
    public Comment newInstance(final Invitation invitation) {
        Comment comment = new Comment(invitation, securityService.getUser());
        return comment;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(final Comment comment) {
        validate(comment);
        commentRepo.save(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateComment(Invitation invitation, String text) {
        Check.isTrue(!invitation.isNew());
        User author = securityService.getUser();

        // find existing comment
        Comment comment = findByInvitationAndAuthor(invitation, author, 0);
        if (comment != null) {
            comment.setText(text);
        } else {
            // no comment for author found -> create new one
            comment = new Comment(text, invitation, author);
        }
        commentRepo.save(comment);
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
