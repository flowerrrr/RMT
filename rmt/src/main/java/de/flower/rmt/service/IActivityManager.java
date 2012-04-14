package de.flower.rmt.service;

import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;

/**
 * @author flowerrrr
 */
public interface IActivityManager {

//    void save(Activity entity);
//
//    Activity loadById(Long id);
//
//    List<Activity> findAll();
//
//    void delete(Long id);

    void onCreateOrUpdate(Event event, boolean isNew);

    void onInvitationMailSent(Event event);

    void onInvitationUpdated(Invitation invitation, Invitation origInvitation);
}
