package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Activity;
import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.activity.EventUpdateMessage;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IActivityManager {

    //    void save(Activity entity);
//
    Activity loadById(Long id);
//
//    List<Activity> findAll();
//
//    void delete(Long id);

    void onCreateOrUpdateEvent(Event event, EventUpdateMessage.Type type);

    void onInvitationMailSent(Event event);

    /**
     * Method must be called before invitation is saved.
     *
     * @param invitation
     * @param origInvitation
     * @param comment
     * @param origComment
     */
    void onInvitationUpdated(Invitation invitation, final Invitation origInvitation, final String comment, final String origComment);

    void onCommentUpdated(Comment comment, Comment origComment);

    List<Activity> findLastN(int num, final int i);

}
